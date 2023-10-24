@echo off
set the_path=%1
set to_find=%2
rem echo %the_path%
rem echo %to_find%
where /r "%the_path%" "%to_find%"> a.txt
type a.txt
