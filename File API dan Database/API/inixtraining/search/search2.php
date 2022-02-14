
<?php 
	//Mendapatkan Nilai Dari Variable
	$nama_pst = $_GET['nama_pst'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query 
	$sql = "SELECT m.nama_mat, k.tgl_mulai_kls, k.tgl_akhir_kls
    FROM materi m JOIN kelas k  ON m.id_mat = k.id_mat
    JOIN detail_kelas dk ON k.id_kls = dk.id_kls
    JOIN peserta p ON p.id_pst = dk.id_pst
    WHERE p.nama_pst = '$nama_pst';";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	while($row = mysqli_fetch_array($r)){
		array_push($result,array(
			"nama_mat"=>$row['nama_mat'],
            "tgl_mulai_kls"=>$row['tgl_mulai_kls'],
            "tgl_akhir_kls"=>$row['tgl_akhir_kls']
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>
