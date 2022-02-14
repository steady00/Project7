
<?php 
	//Mendapatkan Nilai Dari Variable
	$nama_mat = $_GET['nama_mat'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query 
	$sql = "SELECT p.nama_pst
			FROM peserta p JOIN detail_kelas dk ON p.id_pst = dk.id_pst
			JOIN kelas k ON dk.id_kls = k.id_kls
			JOIN materi m ON k.id_mat = m.id_mat
			WHERE m.nama_mat = '$nama_mat';";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	while($row = mysqli_fetch_array($r)){
		array_push($result,array(
			"nama_pst"=>$row['nama_pst']
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>