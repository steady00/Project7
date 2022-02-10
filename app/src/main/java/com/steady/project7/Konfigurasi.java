package com.steady.project7;

public class Konfigurasi {
    //url dimana web API berada
    //melihat data
    public static final String URL_ADD_DETAIL_KELAS = "http://192.168.0.6/inixtraining/detail_kelas/tr_add_detail_kelas.php";
    public static final String URL_GET_ALL_KELAS_DETAIL = "http://192.168.0.6/inixtraining/detail_kelas/tr_datas_detail_kelas.php";
    public static final String URL_DELETE_KELAS_DETAIL = "http://192.168.0.6/inixtraining/detail_kelas/tr_delete_detail_kelas.php";
    public static final String URL_DETAIL_KELAS_DETAIL = "http://192.168.0.6/inixtraining/detail_kelas/tr_detail_detail_kelas.php?id_kls=";

    public static final String URL_ADD_INSTRUKTUR = "http://192.168.0.6/inixtraining/instruktur/tr_add_instruktur.php";
    public static final String URL_GET_ALL_INSTRUKTUR = "http://192.168.0.6/inixtraining/instruktur/tr_datas_instruktur.php";
    public static final String URL_DELETE_INSTRUKTUR = "http://192.168.0.6/inixtraining/instruktur/tr_delete_instruktur.php?id_ins=";
    public static final String URL_DETAIL_INSTRUKTUR = "http://192.168.0.6/inixtraining/instruktur/tr_detail_instruktur.php?id_ins=";
    public static final String URL_UPDATE_INSTRUKTUR = "http://192.168.0.6/inixtraining/instruktur/tr_update_instruktur.php";

    public static final String URL_ADD_KELAS = "http://192.168.0.6/inixtraining/kelas/tr_add_kelas.php";
    public static final String URL_GET_ALL_KELAS = "http://192.168.0.6/inixtraining/kelas/tr_datas_kelas.php";
    public static final String URL_DELETE_KELAS = "http://192.168.0.6/inixtraining/kelas/tr_delete_kelas.php?id_kls=";
    public static final String URL_DETAIL_KELAS = "http://192.168.0.6/inixtraining/kelas/tr_detail_kelas.php?id_kls=";
    public static final String URL_UPDATE_KELAS = "http://192.168.0.6/inixtraining/kelas/tr_update_kelas.php";

    public static final String URL_ADD_MATERI = "http://192.168.0.6/inixtraining/materi/tr_add_materi.php";
    public static final String URL_GET_ALL_MATERI = "http://192.168.0.6/inixtraining/materi/tr_datas_materi.php";
    public static final String URL_DELETE_MATERI = "http://192.168.0.6/inixtraining/materi/tr_delete_materi.php?id_mat=";
    public static final String URL_DETAIL_MATERI = "http://192.168.0.6/inixtraining/materi/tr_detail_materi.php?id_mat=";
    public static final String URL_UPDATE_MATERI = "http://192.168.0.6/inixtraining/materi/tr_update_materi.php";

    public static final String URL_ADD_PESERTA = "http://192.168.0.6/inixtraining/peserta/tr_add_peserta.php";
    public static final String URL_GET_ALL_PESERTA = "http://192.168.0.6/inixtraining/peserta/tr_datas_peserta.php";
    public static final String URL_DELETE_PESERTA = "http://192.168.0.6/inixtraining/peserta/tr_delete_peserta.php?id_pst=";
    public static final String URL_DETAIL_PESERTA = "http://192.168.0.6/inixtraining/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_UPDATE_PESERTA = "http://192.168.0.6/inixtraining/peserta/tr_update_peserta.php";


    //flag JSON Kelas
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID_KLS = "id_kls";
    public static final String TAG_JSON_TGL_MULAI = "tgl_mulai_kls";
    public static final String TAG_JSON_TGL_AKHIR = "tgl_akhir_kls";
    public static final String TAG_JSON_KLS_NAMA_INS = "nama_ins";
    public static final String TAG_JSON_KLS_NAMA_MAT = "nama_mat";

    //flag JSON Peserta
    public static final String TAG_JSON_ID_PST = "id_pst";
    public static final String TAG_JSON_NAMA_PST = "nama_pst";
    public static final String TAG_JSON_EMAIL_PST = "email_pst";
    public static final String TAG_JSON_HP_PST = "hp_pst";
    public static final String TAG_JSON_INSTANSI_PST = "instansi_pst";

    //flag JSON Instruktur
    public static final String TAG_JSON_ID_INS = "id_ins";
    public static final String TAG_JSON_NAMA_INS = "nama_ins";
    public static final String TAG_JSON_EMAIL_INS = "email_ins";
    public static final String TAG_JSON_HP_INS = "hp_ins";

    //flag JSON Materi
    public static final String TAG_JSON_ID_MAT = "id_mat";
    public static final String TAG_JSON_NAMA_MAT = "nama_mat";

    //flag JSON Kelas Detail
    public static final String TAG_JSON_ID_KELAS_KLS_DETAIL = "id_kls";
    public static final String TAG_JSON_MATERI_KLS_DETAIL = "nama_mat";
    public static final String TAG_JSON_JUMLAH_PST = "jum_pst";

    public static final String TAG_JSON_ID_KELAS_KELAS_DETAIL = "id_kls";
    public static final String TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL = "id_detail_kls";
    public static final String TAG_JSON_NAMA_PST_KELAS_DETAIL = "nama_pst";

//--------------------------------------------------------------------------------------------------

    //key and value JSON yang muncul di browser KELAS
    public static final String KEY_ID_KLS = "id_kls";
    public static final String KEY_TGL_MULAI = "tgl_mulai_kls";
    public static final String KEY_TGL_AKHIR = "tgl_akhir_kls";
    public static final String KEY_KLS_NAMA_INS = "nama_ins";
    public static final String KEY_KLS_NAMA_MAT = "nama_mat";
    //public static final String KEY_ID_INS = "id_ins";
    //public static final String KEY_ID_MAT = "id_mat";

    //key PESERTA
    public static final String KEY_ID_PST = "id_pst";
    public static final String KEY_NAMA_PST = "nama_pst";
    public static final String KEY_EMAIL_PST = "email_pst";
    public static final String KEY_HP_PST = "hp_pst";
    public static final String KEY_INSTANSI_PST = "instansi_pst";

    //key Instruktur
    public static final String KEY_ID_INS = "id_ins";
    public static final String KEY_NAMA_INS = "nama_ins";
    public static final String KEY_EMAIL_INS = "email_ins";
    public static final String KEY_HP_INS = "hp_ins";

    //key Materi
    public static final String KEY_ID_MAT = "id_mat";
    public static final String KEY_NAMA_MAT = "nama_mat";

    public static final String PST_ID = "pst_id";
    public static final String INS_ID = "ins_id";
    public static final String MAT_ID = "mat_id";
    public static final String KLS_ID = "kls_id";
    public static final String KELAS_KLS_DETAIL_ID = "kls_detail_id";

}
