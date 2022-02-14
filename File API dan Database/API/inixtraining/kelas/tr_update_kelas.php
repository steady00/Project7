<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id_kls = $_POST['id_kls'];
		$tgl_mulai_kls = $_POST['tgl_mulai_kls'];
		$tgl_akhir_kls = $_POST['tgl_akhir_kls'];
		$nama_ins = $_POST['nama_ins'];
		$nama_mat = $_POST['nama_mat'];
        //$id_ins = $_POST['id_ins'];
        //$id_mat = $_POST['id_mat'];
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		//$sql = "UPDATE kelas SET tgl_mulai_kls = '$tgl_mulai_kls', tgl_akhir_kls = '$tgl_akhir_kls' WHERE id_kls = $id_kls;";
		$sql = "UPDATE kelas
				SET tgl_mulai_kls = '$tgl_mulai_kls', tgl_akhir_kls = '$tgl_akhir_kls', id_ins =(
					SELECT id_ins
					FROM instruktur
					WHERE nama_ins = '$nama_ins'), 
				id_mat =(
					SELECT id_mat
					FROM materi
					WHERE nama_mat = '$nama_mat')
				WHERE id_kls = $id_kls;";

		//UPDATE kelas SET tgl_mulai_kls = '2026-02-01', tgl_akhir_kls = '2026-03-01', id_ins = '$id_ins', id_mat = '$id_mat' WHERE id_kls = 21;
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Kelas';
		}else{
			echo 'Gagal Update Data Kelas';
		}
		
		mysqli_close($con);
	}
?>