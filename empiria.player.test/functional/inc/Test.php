<?php
require_once ("PHPUnit/Extensions/SeleniumTestCase.php");
require_once ("PHPUnit/Framework.php");
require_once ("fileSupport.php");
require_once ("textSupport.php");
require_once ("DBSupport.php");
require_once ("conf.php");

class Test extends PHPUnit_Extensions_SeleniumTestCase {
	
	public $config = array();
	public $browser;
	public $mainAddress;
	public $login;
	public $password;
	public $schoolCode;
	//db
	public $dbUser;
	public $dbPass;
	public $dbHost;
	public $dbName;

	public function __construct($name) {
		
		parent::__construct($name);
		$this->config 		= new Config();
		$conf = $this->config->getConf();
		$this->browser		= $conf["BROWSER"];
		$this->mainAddress	= $conf["SERVER_ADDRESS"];
		$this->login		= $conf["LOGIN"];
		$this->password		= $conf["PASSWORD"];
		$this->schoolCode	= $conf["SCHOOL_CODE"];
		$this->courseUrl	= $conf["COURSE_URL"];
		//db
		$this->dbUser		= $conf["DBUSER"];
		$this->dbPass		= $conf["DBPASS"];
		$this->dbHost		= $conf["DBHOST"];
		$this->dbName		= $conf["DBNAME"];
	}
	
	/*
	* Czyszczenie bazy danych
	*/
	public function clearLmsDB() {
		$db = new DBSupport();
		$db->connect($this->dbHost, $this->dbUser, $this->dbPass);
		$db->removeDatabase($this->dbName);
		$db->createDatabase($this->dbName);
		$db->closeConnection();

		// create a new CURL resource
		$ch = curl_init();
		// set URL and other appropriate options
		curl_setopt($ch, CURLOPT_URL, $this->mainAddress."ctrl.php/fw/dbinit");
		curl_setopt($ch, CURLOPT_RETURNTRANSFER , true);
		curl_setopt($ch, CURLOPT_HEADER, false);
		// grab URL and pass it to the browser
		curl_exec($ch);
		// close CURL resource, and free up system resources
		curl_close($ch);
	}
}

?>