@echo off
dir="%~dp0"
java -cp "%dir/lib/*;%dir/robots/*;%dir/etc/" com.reubenpeeris.wippen.engine.Wippen %*
