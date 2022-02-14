<?php 
	//Mendapatkan Nilai Dari Variable
	$tgl_mulai_kls = $_GET['tgl_mulai_kls'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query 
	$sql = "SELECT COUNT(dk.id_pst) jum_pst
    FROM detail_kelas dk JOIN kelas k ON dk.id_kls = k.id_kls
    WHERE YEAR(k.tgl_mulai_kls) = '$tgl_mulai_kls';";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
    array_push($result,array(
		"jum_pst"=>$row['jum_pst']
	));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>