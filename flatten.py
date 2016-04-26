#! /usr/local/bin/python

from git import Repo
import os
import io
import shutil
import sys
import getopt
import tempfile
import argparse

IGNORE_PATTERNS = ('.git', ".DS_Store")
SAFE_CHARS = ["-", "_", "."]
MAX_LENGTH = 100

STUDENT="student"
DEVELOP="develop"
DEVELOP_DEFAULT = "all develop branches"



def flatten(repo_dir, student, develop_branches, remove_branches):

    repo = Repo(repo_dir)


    if develop_branches == DEVELOP_DEFAULT:
        develop_branches = [branch for branch in repo.branches if DEVELOP in branch.name]

    if remove_branches:
        remove_local_branches(repo, student, develop_branches)

    flat = len(develop_branches) == 1

    # print develop_branches

    try:
        temp_dir = tempfile.mkdtemp()
        try:
            current_branch = repo.active_branch
            print "Stashing"
            repo.git.stash()

            for develop in develop_branches:
                to_temp_dir(repo, repo_dir, develop, temp_dir, flat)
            # insert_diff_links(temp_dir)
            snapshots_to_student_branch(repo, student, temp_dir, repo_dir)
        finally:
            repo.git.checkout(current_branch)
            print "Popping"
            if repo.git.stash("list"):
                repo.git.stash("pop")
    finally:
        if os.path.exists(temp_dir):
            shutil.rmtree(temp_dir)

    print "Done! Review and commit the", student, "branch at your leisure."
    print "Then run $ git push --all --prune"


def remove_local_branches(repo, student, develop_branches):
    for branch in repo.branches:
        if branch.name != student and branch not in develop_branches:
            print "Removing local branch:", branch.name
            repo.git.branch(branch.name, "-D")


def to_temp_dir(repo, repo_dir, develop, temp_dir, flat):
    for rev in repo.git.rev_list(develop).split("\n"):
        commit = repo.commit(rev)
        branch_name = clean_commit_message(commit.message)
        if "Exercise" in branch_name or "Solution" in branch_name:
            if branch_name in repo.branches:
                repo.git.branch(branch_name, "-D")
            new_branch = repo.create_head(branch_name)
            new_branch.set_commit(rev)

            repo.git.checkout(commit)
            print "Saving snapshot of:", branch_name
            repo.git.clean("-fdx")
            if flat:
                target_dir = os.path.join(temp_dir, branch_name)
            else:
                _, folder_name = develop.name.split("-")
                target_dir = os.path.join(temp_dir, folder_name, branch_name)

            shutil.copytree(repo_dir, target_dir,
                            ignore=shutil.ignore_patterns(*IGNORE_PATTERNS))
            with open(os.path.join(target_dir, "README.md"), "a") as readme:
                number, _, name = branch_name.split("-")
                readme.write(DIFF_FORMAT.format(number=number, name=name))


def clean_commit_message(message):
    first_line = message.split("\n")[0]
    safe_message = "".join(
        c for c in message if c.isalnum() or c in SAFE_CHARS).strip()
    return (safe_message[:MAX_LENGTH]
            if len(safe_message) >
            MAX_LENGTH else safe_message)


DIFF_FORMAT = """

You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/{number}-Exercise-{name}.zip), \
and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/{number}-Solution-{name}.zip). \
Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/{number}-Exercise-{name}...{number}-Solution-{name}).

"""


def insert_diff_links(temp_dir):
    for item in os.listdir(temp_dir):
        number, _, name = item.split("-")
        with open(os.path.join(temp_dir, item, "README.md"), "a") as readme:
            readme.write(DIFF_FORMAT.format(number=number, name=name))


def snapshots_to_student_branch(repo, student, temp_dir, repo_dir):
    repo.git.checkout(student)
    for item in os.listdir(temp_dir):
        source_dir = os.path.join(temp_dir, item)
        target_dir = os.path.join(repo_dir, item)

        if os.path.exists(target_dir):
            shutil.rmtree(target_dir)
        print "Copying: ", item
        shutil.copytree(source_dir, target_dir)


DESCRIPTION = "This script "

EPILOG = " To make changes to "


def main():
    parser = argparse.ArgumentParser(
        description=DESCRIPTION,
        epilog=EPILOG,
        formatter_class=argparse.ArgumentDefaultsHelpFormatter)

    parser.add_argument('-b', '--remove',
                        action='store_true',
                        help='delete all local branches except the student and develop branches')

    parser.add_argument('-d', '--directory',
                        default=os.getcwd(),
                        help="the directory of the repository")

    parser.add_argument('-s', '--student', default=STUDENT,
                        help="branch where snapshots will be copied")

    parser.add_argument('develop_branches',
                        nargs="*",
                        default=DEVELOP_DEFAULT,
                        help = "the branches where snapshots will be copied from")

    parsed = parser.parse_args()

    flatten(
        parsed.directory,
        parsed.student,
        parsed.develop_branches,
        parsed.remove
    )

if __name__ == "__main__":
    sys.exit(main())
