<?php

class fileSupport
{
	function initializeLogFile($logPath, $logName, $main=false)
	{
		// logPath ,np. "C:\selenium\my_logs"
		if (!is_dir($logPath)) mkdir($logPath);
		if (is_file("".$logPath."/test_".$logName."_".date('Y-m-d').".html")) unlink("".$logPath."/test_".$logName."_".date('Y-m-d').".html");
		$main==true ? $fileName=$logPath."/".$logName : $fileName = "".$logPath."/test_".$logName."_".date('Y-m-d').".html";
		$handle = fopen($fileName, 'a');
		return $handle;
	}
	
	function addTextToLog($text, $handle)
	{
		fwrite($handle, $text);
	}
	
	function setResult($result, $testName)
	{
		if ($result=="OK") $result = "<font color='green'>OK</font>";
		else if ($result=="ERROR") $result = "<font color=red>ERROR</font>";
		else if ($result!="ERROR" && $result!="OK") $result = "<font color=red>ERROR</font>".$result;
		return "<tr><td>".date("Y-m-d H:i:s")."</td><td>".$testName."</td><td>".$result."</td>"."</tr>";
	}
}

?>