<?php
/*
* G³ówny plik uruchamiaj¹cy testy
*
* Struktura katalogów dla testów
*testy
*  TestRun
*  inc
*  tests
*      lms6-rek
*             jakis test
*            jakis test2
*      yPublisher-12
*             jakis test
*            jakis test2 
*
*
 * Product ids
 * lms6-rek: 53
 */
 
require_once ("inc/Test.php");

class TestRun extends Test {
	
    public static function suite() {

	$sep = DIRECTORY_SEPARATOR;
	$suite = new PHPUnit_Framework_TestSuite();
	$argv  = $_SERVER["argv"];
	$productid = array (
							1	=> "yPublisher-Trunk",
							2	=> "lms-6.1",
							3	=> "lms-6.1-gfp",
							18	=> "lms-ags",
							49	=> "lms-harspelling",
							59	=> "lms-hod",
							5	=> "lms-maltoetsen",
							12	=> "lms-mpp",
							6	=> "lms-nor",
							23	=> "lms-oup",
							48	=> "lms-pe-DEV",
							47	=> "lms-pe-STABLE",
							4	=> "lms-poe",
							50	=> "lms-proactive",
							51	=> "lms-science",
							52	=> "lms-sureskills",
							13	=> "lms-thai",
							53	=> "lms6-rek",
							60  => "yPublisher-1.3",
							67  => "yAuthor-STABLE",
							359  => "yPublisher-2.3",
						);
							
	/*
	* Kolejno
	* 1. Pobieram prze³¹cznik --product-id oraz jego wartoœæ
	* 2. Wg pobranej wartoœci uruchamiam testy z tablicy $productid
	*/
	
	if (in_array("--product-id", $argv))
	{
		// wyszukuje przelacznik --product-id ktory powinien zawierac numer produktu ktorego test ma zostac przeprowadzony
		$key = array_search("--product-id", $argv);
		if (isset($argv[$key+1]))
		{
			$id = $argv[$key+1];
			if (isset($productid[$id])) {
				$dir = pathinfo(__FILE__, PATHINFO_DIRNAME).$sep."tests".$sep.$productid[$id];
				
				if ($handle = opendir($dir)) {
				
					// uaktualniam listê testów dla wybranego produktu
						// pobieram plik csv
						// create a new CURL resource
//						$ch = curl_init();
						// set URL and other appropriate options
//						curl_setopt($ch, CURLOPT_URL, "http://devin.intranet.ydp/bds/ctrl.php/site/portal/test/test_a?_act=test_export&p=".$id);
//						curl_setopt($ch, CURLOPT_RETURNTRANSFER , true);
//						curl_setopt($ch, CURLOPT_HEADER, false);
						// grab URL and pass it to the browser
//						$fileList = curl_exec($ch);
						// close CURL resource, and free up system resources
//						curl_close($ch);
						$a_line = explode("\n", $fileList);
						// usuwam stare pliki z testami
						/*if ($oldTestHandle = opendir(pathinfo(__FILE__, PATHINFO_DIRNAME).$sep."tests".$sep.$productid[$id])) {
							while (false !== ($oldTestFile = readdir($oldTestHandle))) {
								if ($oldTestFile != "." && $oldTestFile != "..") {
									unlink (pathinfo(__FILE__, PATHINFO_DIRNAME).$sep."tests".$sep.$productid[$id].$sep.$oldTestFile);
								}
							}
							closedir($oldTestHandle);
						}*/

						for ($j=0; $j<count($a_line); $j++) {
							$a_link = explode(",", $a_line[$j]);
							$link = $a_link[1];
							if ($link!="")
								exec("svn export ".$link." ".pathinfo(__FILE__, PATHINFO_DIRNAME).$sep."tests".$sep.$productid[$id]." --force");
						}
					
					$file = readdir($handle);
					$testFile = glob($dir.$sep."Test*.php");
					
					if (!empty($testFile)) {
						for ($i=0; $i<count($testFile); $i++) {
							require_once ("".$testFile[$i]."");
							$fileName = substr($testFile[$i], strlen($dir)+1, -4);
							$className = explode("_", $fileName);
							$suite->addTest(new $className[1]($className[0]));
						}
					}
				}
			}
			else {
				throw new Exception("Product id ".$id." doesn't exists.");
			}
		}
	}
	else {
		throw new Exception("\n\nWARNING: You must set product id (ex. TestRun.php&productid=53) to run test.\n\n");
	}
	
	
	return $suite;
    }
}
?>