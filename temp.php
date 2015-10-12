<?php   

  sendresponse();
    

  //----------------------------------------------------------------
  function sendresponse() {
      
    // om te testen even wat hardcoded gegevens gebruiken
    $matchresult  = "1"; // 1 = geslaagde match, 0 = niet geslaagd
    $destination  = "2"; // 1 = aanvraag 2 = aanbod
    $min          = "3";
    $sec          = "20";
    $adres        = "Beatrixlaan 234";
    $bestemming   = "Dorpstraat 34";
    $passagier    = "Henk";
    $telefoon     = "0633223322";
    $toegangscode = "BBB";  
  
    $fields = array( "matchresult"  => $matchresult, 
                     "destination"  => $destination,
                     "min"          => $min,
                     "sec"          => $sec,
                     "ophaaladres"  => $adres,
                     "bestemming"   => $bestemming,
                     "passagier"    => $passagier,
                     "telefoon"     => $telefoon,
                     "toegangscode" => $toegangscode );
  
    header("Content-type: application/json");
    echo json_encode( $fields );
  
  }
 
?>