<?php
class Config {

	var $conf = array	(
							//DB
							//"DBHOST"			=> "192.168.5.231",
							//"DBNAME"			=> "test_yp_trunk",
						
							//"BROWSER"			=> "*iexplore",
							"BROWSER"			=> "*firefox c:/Program Files/Mozilla Firefox/firefox.exe",
							"SERVER_ADDRESS"	=> "http://ypdev.ydp.eu/ctrl.php/site/login/login",
							"LOGIN"				=> "admin",
							"PASSWORD"			=> "pasikonik",
						);
	
	function getConf() {
		return $this->conf;
	}
	
}
?>