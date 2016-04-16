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

STUDENT_BRANCH = "student"
DEVELOP_BRANCH = "develop"
ORIGIN = "origin"
STASH_NAME = "flattenStash"


def flatten(
        repo_dir,
        student,
        develop,
        remote,
        remove_branches,
        generate_branches):


    repo = Repo(repo_dir)
    remote = repo.remote(remote)

    if remove_branches:
        remove_local_branches(repo, student, develop)

    try:
        temp_dir = tempfile.mkdtemp()
        try:
            current_branch = repo.active_branch
            print "Stashing"
            repo.git.stash()

            to_temp_dir(repo, repo_dir, develop, temp_dir, generate_branches)
            insert_diff_links(temp_dir)


            snapshots_to_student_branch(repo, student, temp_dir, repo_dir)

        finally:
            repo.git.checkout(current_branch)
            print "Popping"
            if repo.git.stash("list"):
                repo.git.stash("pop")

    finally:
        if os.path.exists(temp_dir):
            shutil.rmtree(temp_dir)

    # print "Pushing changes"
    # self.remote.push(all=True, prune = True)

    print "Done! Review and commit the", student, "branch at your leisure."
    print "Then run $ git push {} --all --prune".format(remote.name)


def remove_local_branches(repo, student, develop):
    for branch in repo.branches:
        if branch.name != student and branch.name != develop:
            print "Removing local branch:", branch.name
            repo.git.branch(branch.name, "-D")


def to_temp_dir(repo, repo_dir, develop, temp_dir, generate_branches):
    for rev in repo.git.rev_list(develop).split("\n"):
        commit = repo.commit(rev)
        branch_name = clean_commit_message(commit.message)
        if "Exercise" in branch_name or "Solution" in branch_name:

            if generate_branches:
                print "Creating local branch:", branch_name
                new_branch = repo.create_head(branch_name)
                new_branch.set_commit(rev)

            repo.git.checkout(commit)
            print "Saving snapshot of:", branch_name
            repo.git.clean("-fdx")
            target_dir = os.path.join(temp_dir, branch_name)
            shutil.copytree(repo_dir, target_dir,
                            ignore=shutil.ignore_patterns(*IGNORE_PATTERNS))


def clean_commit_message(message):
    first_line = message.split("\n")[0]
    safe_message = "".join(
        c for c in message if c.isalnum() or c in SAFE_CHARS).strip()
    return (safe_message[:MAX_LENGTH]
            if len(safe_message) >
            MAX_LENGTH else safe_message)

def insert_diff_links(temp_dir):

    for item in os.listdir(temp_dir):
        print item
        number, _, name = item.split("-")
        print number, name
        print os.path.join(temp_dir, item, "README.md")
        with open(os.path.join(temp_dir, item, "README.md"), "a") as readme:
            readme.write("\n\nhttps://github.com/udacity/ud843-QuakeReport/compare/{number}-Exercise-{name}...{number}-Solution-{name}\n".format(number = number, name = name))


def snapshots_to_student_branch(repo, student, temp_dir, repo_dir):
    repo.git.checkout(student)
    for item in os.listdir(temp_dir):
        source_dir = os.path.join(temp_dir, item)
        target_dir = os.path.join(repo_dir, item)

        if os.path.exists(target_dir):
            shutil.rmtree(target_dir)
        print "Copying: ", item
        shutil.copytree(source_dir, target_dir)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--repo-dir', '-rd', default=os.getcwd())
    parser.add_argument('--student-branch', '-s', default=STUDENT_BRANCH)
    parser.add_argument('--develop-branch', '-d', default=DEVELOP_BRANCH)
    parser.add_argument('--remote', '-r', default=ORIGIN)
    parser.add_argument('--remove-branches', '-b', action='store_true')
    parser.add_argument('--generate-branches', '-g', action='store_true')

    parsed = parser.parse_args()

    print parsed

    flatten(
        parsed.repo_dir,
        parsed.student_branch,
        parsed.develop_branch,
        parsed.remote,
        parsed.remove_branches,
        parsed.generate_branches

    )

if __name__ == "__main__":
    sys.exit(main())
