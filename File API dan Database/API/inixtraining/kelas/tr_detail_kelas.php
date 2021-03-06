<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_kls = $_GET['id_kls'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT k.id_kls, k.tgl_mulai_kls , k.tgl_akhir_kls , i.nama_ins , m.nama_mat 
	FROM kelas k JOIN instruktur i ON (k.id_ins = i.id_ins)
	JOIN materi m ON (k.id_mat = m.id_mat)
	WHERE k.id_kls=$id_kls";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			/*"id_kls"=>$row['id_kls'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
            "id_ins"=>$row['id_ins'],
            "id_mat"=>$row['id_mat']*/
			"id_kls"=>$row['id_kls'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
			"nama_ins"=>$row['nama_ins'],
			"nama_mat"=>$row['nama_mat']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>