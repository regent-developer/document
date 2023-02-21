# Git Hook
|  Git Hook   | 调用时机  |说明  |
|  ----  | ----  |----  |
|  pre-applypatch | git am执行前 | |
|  applypatch-msg	 | git am执行前 | |
|  post-applypatch | git am执行后 | 不影响git am的结果|
|  pre-commit | git commit执行前 | 可以用git commit --no-verify绕过|
|  commit-msg | git commit执行前 | 可以用git commit --no-verify绕过|
|  post-commit | git commit执行后 | 不影响git commit的结果|
|  pre-merge-commit | git merge执行前 |可以用git merge --no-verify绕过。 |
|  prepare-commit-msg | git commit执行后，编辑器打开之前 | |
|  pre-rebase | git rebase执行前 | |
|  post-checkout | git checkout或git switch执行后 | 如果不使用--no-checkout参数，则在git clone之后也会执行|
|  post-merge | git commit执行后 | 在执行git pull时也会被调用|
|  pre-push | git push执行前 | |
|  pre-receive	 | git-receive-pack执行前 | |
|  update	 |  | |
|  post-receive | git-receive-pack执行后 | 不影响git-receive-pack的结果|
|  post-update	 | 当 git-receive-pack对 git push 作出反应并更新仓库中的引用时 | |
|  push-to-checkout | 当``git-receive-pack对git push做出反应并更新仓库中的引用时，以及当推送试图更新当前被签出的分支且receive.denyCurrentBranch配置被设置为updateInstead`时 | |
|  pre-auto-gc | git gc --auto执行前 | |
|  post-rewrite	 | 执行git commit --amend或git rebase时 | |
|  sendemail-validate | git send-email执行前 | |
|  fsmonitor-watchman | 配置core.fsmonitor被设置为.git/hooks/fsmonitor-watchman或.git/hooks/fsmonitor-watchmanv2时 | |
|  p4-pre-submit	 | git-p4 submit执行前 | 可以用git-p4 submit --no-verify绕过|
|  p4-prepare-changelist | git-p4 submit执行后，编辑器启动前 | 可以用git-p4 submit --no-verify绕过|
|  p4-changelist | git-p4 submit执行并编辑完changelist message后 | 可以用git-p4 submit --no-verify绕过|
| p4-post-changelist  | git-p4 submit执行后 | |
|  post-index-change | 索引被写入到read-cache.c do_write_locked_index后 | |


