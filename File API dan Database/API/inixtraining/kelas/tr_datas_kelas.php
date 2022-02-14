<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	//$sql = "SELECT * FROM kelas";
	$sql = "SELECT k.id_kls, k.tgl_mulai_kls , k.tgl_akhir_kls , i.nama_ins , m.nama_mat
			FROM kelas k JOIN instruktur i ON (k.id_ins = i.id_ins)
			JOIN materi m ON (k.id_mat = m.id_mat)
			ORDER BY k.id_kls, k.tgl_mulai_kls";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			/*"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
            "id_ins"=>$row['id_ins'],
            "id_mat"=>$row['id_mat']*/
			"id_kls"=>$row['id_kls'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
			"nama_mat"=>$row['nama_mat']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>