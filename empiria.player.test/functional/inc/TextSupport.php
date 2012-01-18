<?php
require_once ("conf.php");
	
class textSupport
{
	
	function createRandomWord($minTextSize, $maxTextSize)
	{
		$letters = array('a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j' ,'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'r', 'R', 's' ,'S', 't', 'T', 'u', 'U', 'w', 'W', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'); 
		$wordSize = rand ($minTextSize, $maxTextSize);
		$randomWord = "";
		
		// generuje losowy login
		for ($i=0; $i<$wordSize; $i++)
		{
			$randomLetter = rand(0, count($letters)-1);
			$randomWord .= $letters[$randomLetter];
		}
		return $randomWord;
	}
	
	function createRandomWord2($minTextSize, $maxTextSize)
	{
		$letters = array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','W', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'); 
		$wordSize = rand ($minTextSize, $maxTextSize);
		$randomWord = "";
		
		// generuje losowy login
		for ($i=0; $i<$wordSize; $i++)
		{
			$randomLetter = rand(0, count($letters)-1);
			$randomWord .= $letters[$randomLetter];
		}
		return $randomWord;
	}
	
	function getMessage($htmlSource)
	{
		preg_match('|class=vmessages>.*.<li>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $msg);
		preg_match('|class=verrors>.*.<li>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $err);
		if (isset($msg[1])) $info=trim($msg[1]);
		elseif (isset($err[1])) $info=trim($err[1]);
		else $info = "";
		
		return $info;
	}
	
	function getLmsMessage($htmlSource)
	{
		$conf = new Config();
		$abrowser = str_word_count($conf->conf["BROWSER"], 1);
		
		if (in_array("iexplore", $abrowser)) {
			preg_match('|class=message>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $msg);
			preg_match('|class=error>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $err);
		}
		else if (in_array("firefox", $abrowser)) {
			preg_match('|class=.message.>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $msg);
			preg_match('|class=.*.error.*.>(.*)<\/li>.*.<\/div>|Uis', $htmlSource, $err);
		}
		
		if (isset($msg[1])) $info=trim($msg[1]);
		elseif (isset($err[1])) $info=trim($err[1]);
		else $info = "";
		
		return $info;
	}
}
?>