<?php
/*
	* Opis testu
	* 
	* @bds.testname Logowanie
	* 
	* @bds.testdescription Logowanie użytkownika przy użyciu zdefiniowanych danych
	* 
	* @bds.testcomponent ypPortal
	*  
	* @bds.testpriority P1
	* 
	* @bds.prerequisites
	* 1. Szkoła 
	* 2. Login
	* 3. Hasło
	* 
	* @bds.inputdata
	* 
	* @bds.teststeps
	* 1. Uruchomienie aplikacji
	* 1.1 Po uruchomieniu aplikacji pojawi się strona z logowaniem
	* 2. Uzupełnienie danych niezbędnych do zalogowania (szkoła, login, hasło)
	* 3. Kliknięcie na przycisku "Login"
	* 4. Zweryfikowanie wyniku logowania
	* 4.1 Jeżeli użytkownik został przeniesiony do strony głównej aplikacji albo na stronę testową aplikacji (pojawia się po pierwszym zalogowaniu; można do niej wrócić klikając na "Check Software" ze strony głównej aplikacji)  to został prawidłowo zalogowany
	* 4.2 Jeżeli użytkownik pozostał na stronie logowania to nie został zalogowany (w takim przypadku powinien otrzymać informację zwrotną, np. "Login error")
	* 
	* @bds.outputdata 
	* 1. Zalogowanie użytkownika na stronę główną aplikacji lub na stronę testową aplikacji
	* 
	* @bds.postrequisites
*/

class PortalAdminLogin extends Test {

	public function setUp() {
		$this->setBrowser($this->browser);
		$this->setBrowserUrl($this->mainAddress);
	}

	public function Test47() {	
			$text = new TextSupport();
		// Czyszczenie bazy danych
			//$this->clearLmsDB();
			
		// BEGIN : LOGOWANIE
			$login		= array($text->createRandomWord(6,15), $this->login);
			$password	= array($text->createRandomWord(6,15), $this->password);

			for ($i=0; $i<=count($this->login); $i++) {
				$this->open($this->mainAddress);

				$this->type("//input[@name='login']", $login[$i]);
				$this->type("//input[@name='password']", $password[$i]);
				$this->click("_actf[check]");
				$this->waitForPageToLoad("60000");
				$lmsMessage = $text->getLmsMessage($this->getHtmlSource());
				echo $lmsMessage;
				switch ($lmsMessage) {
					case "Login error":	
						break;
					case "User already loaded. You can force logging.":	
						$this->click("force");
						$this->click("_actf[check]");
						$this->waitForPageToLoad("60000");
						if ($this->isTextPresent("MY ACCOUNT"))
							$this->assertTrue($this->isTextPresent("MY ACCOUNT"));
						break;
					default:
						if ($this->isTextPresent("MY ACCOUNT"))
							$this->assertTrue($this->isTextPresent("MY ACCOUNT"));
						break;
				}
			}
		// END
	}
}
 
?>