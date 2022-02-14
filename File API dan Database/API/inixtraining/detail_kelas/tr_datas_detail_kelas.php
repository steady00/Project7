<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT dk.id_kls, m.nama_mat, COUNT(dk.id_pst) jum_pst 
	FROM detail_kelas dk JOIN kelas k ON(dk.id_kls = k.id_kls) 
	JOIN materi m ON (k.id_mat = m.id_mat) 
	GROUP BY dk.id_kls;";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_kls"=>$row['id_kls'],
			"nama_mat"=>$row['nama_mat'],
			"jum_pst"=>$row['jum_pst']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>