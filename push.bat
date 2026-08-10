@echo off
set /p msg="コミットメッセージ: "
git add .
git commit -m "%msg%"
git push origin main