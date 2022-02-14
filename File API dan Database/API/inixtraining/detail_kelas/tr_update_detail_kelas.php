<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id_detail_kls = $_POST['id_detail_kls'];
		$id_kls = $_POST['id_kls'];
		//$id_pst = $_POST['id_pst'];
		$nama_pst = $_POST['nama_pst'];
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		//$sql = "UPDATE detail_kelas SET id_kls = '$id_kls', id_pst = '$id_pst' WHERE id_detail_kls = $id_detail_kls;";
		$sql = "UPDATE detail_kelas
		SET id_kls = '$id_kls', id_pst = (SELECT id_pst
        							FROM peserta
                             		WHERE nama_pst = '$nama_pst')
		WHERE id_detail_kls = $id_detail_kls;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Detail Kelas';
		}else{
			echo 'Gagal Update Data Detail Kelas';
		}
		
		mysqli_close($con);
	}
?>