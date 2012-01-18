<?php

class DBSupport
{
	var $link;
	
	public function connect($dbHost, $dbUser, $dbPass) {
		if (!$this->link = mysql_connect($dbHost, $dbUser, $dbPass)) {
			die('Could not connect: ' . mysql_error());
		}
	}
	
	public function removeDatabase($dbName) {
		$result = mysql_query("DROP DATABASE IF EXISTS ".$dbName."");
		return $result;
	}
	
	public function createDatabase($dbName) {
		$result = mysql_query("CREATE DATABASE IF NOT EXISTS ".$dbName."");
		return $result;
	}
	
	public function closeConnection() {
		mysql_close($this->link);
	}
}
?>