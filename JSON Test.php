<?php
mysql_connect("host","username", "password");
mysql_select_db("RaiderPoint");

$q=mysql_query("SELECT Name FROM Event");
while($e=mysql_fetch_assoc($q))
	$output[]=$e;

print(json_encode($output));

mysql_close();
?>