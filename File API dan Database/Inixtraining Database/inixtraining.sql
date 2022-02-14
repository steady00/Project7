-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 13 Feb 2022 pada 07.41
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inixtraining`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_kelas`
--

CREATE TABLE `detail_kelas` (
  `id_detail_kls` int(10) NOT NULL,
  `id_kls` int(10) NOT NULL,
  `id_pst` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_kelas`
--

INSERT INTO `detail_kelas` (`id_detail_kls`, `id_kls`, `id_pst`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 2, 11),
(12, 2, 12),
(13, 2, 13),
(14, 2, 14),
(15, 2, 15),
(16, 2, 16),
(17, 2, 17),
(18, 2, 18),
(19, 2, 19),
(20, 2, 20),
(21, 3, 21),
(22, 3, 22),
(23, 3, 23),
(24, 3, 24),
(25, 3, 25),
(26, 3, 26),
(27, 3, 27),
(28, 3, 28),
(29, 3, 29),
(30, 3, 30),
(31, 4, 31),
(32, 4, 32),
(33, 4, 33),
(34, 4, 34),
(35, 4, 35),
(36, 4, 36),
(37, 4, 37),
(38, 4, 38),
(39, 4, 39),
(40, 4, 40),
(41, 5, 41),
(42, 5, 42),
(43, 5, 43),
(44, 5, 44),
(45, 5, 45),
(46, 5, 46),
(47, 5, 47),
(48, 5, 48),
(49, 5, 49),
(50, 5, 50),
(51, 6, 51),
(52, 6, 52),
(53, 6, 53),
(54, 6, 54),
(55, 6, 55),
(56, 6, 56),
(57, 6, 57),
(58, 6, 58),
(59, 6, 59),
(60, 6, 60),
(61, 7, 61),
(62, 7, 62),
(63, 7, 63),
(64, 7, 64),
(65, 7, 65),
(66, 7, 66),
(67, 7, 67),
(68, 7, 68),
(69, 7, 69),
(70, 7, 70),
(71, 8, 71),
(72, 8, 72),
(73, 8, 73),
(74, 8, 74),
(75, 8, 75),
(76, 8, 76),
(77, 8, 77),
(78, 8, 78),
(79, 8, 79),
(80, 8, 80),
(81, 9, 81),
(82, 9, 82),
(83, 9, 83),
(84, 9, 84),
(85, 9, 85),
(86, 9, 86),
(87, 9, 87),
(88, 9, 88),
(89, 9, 89),
(90, 9, 91),
(91, 10, 91),
(92, 10, 92),
(93, 10, 93),
(94, 10, 94),
(95, 10, 95),
(96, 10, 96),
(97, 10, 97),
(98, 10, 98),
(99, 10, 99),
(100, 10, 100),
(102, 21, 11),
(113, 21, 1),
(122, 21, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `instruktur`
--

CREATE TABLE `instruktur` (
  `id_ins` int(10) NOT NULL,
  `nama_ins` varchar(50) NOT NULL,
  `email_ins` varchar(50) NOT NULL,
  `hp_ins` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `instruktur`
--

INSERT INTO `instruktur` (`id_ins`, `nama_ins`, `email_ins`, `hp_ins`) VALUES
(1, 'Yusuf Rizal', 'yusuf@gmail.com', '08112345676'),
(2, 'Tine Sopa', 'tine.sopa@gmail.com', '08349856091'),
(3, 'Didik Partono', 'partono.didik@gmail.com', '08767789350'),
(4, 'Kalvin Klein', 'kalvin@gmail.com', '08384775793'),
(5, 'Somad Rahmad', 'somad@gmail.com', '08199039404'),
(6, 'Lasumardi', 'lasum@gmail.com', '08747457839'),
(7, 'Rondi Hidayat', 'rondi.hid@gmail.com', '08999888777'),
(8, 'Imanuel Putra', 'imanuel@gmail.com', '08577668890'),
(9, 'Santoso Batubara', 'batubara@gmail.com', '08234890090'),
(10, 'Renaldi Kumis', 'kumis@gmail.com', '08400008991');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kelas`
--

CREATE TABLE `kelas` (
  `id_kls` int(10) NOT NULL,
  `tgl_mulai_kls` date NOT NULL,
  `tgl_akhir_kls` date NOT NULL,
  `id_ins` int(10) NOT NULL,
  `id_mat` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kelas`
--

INSERT INTO `kelas` (`id_kls`, `tgl_mulai_kls`, `tgl_akhir_kls`, `id_ins`, `id_mat`) VALUES
(1, '2022-01-01', '2022-03-01', 10, 10),
(2, '2022-03-02', '2022-05-02', 1, 1),
(3, '2022-05-03', '2022-07-03', 2, 2),
(4, '2022-07-04', '2022-09-04', 3, 3),
(5, '2022-09-05', '2022-11-05', 4, 4),
(6, '2022-11-06', '2022-12-06', 5, 5),
(7, '2022-12-07', '2023-02-07', 6, 6),
(8, '2023-02-08', '2023-04-08', 7, 7),
(9, '2023-04-09', '2023-06-09', 8, 8),
(10, '2023-06-10', '2023-08-10', 9, 9),
(11, '2023-10-11', '2023-12-11', 10, 10),
(12, '2023-12-12', '2024-01-12', 1, 1),
(13, '2024-01-13', '2024-03-13', 2, 2),
(14, '2024-03-14', '2024-04-14', 3, 3),
(15, '2024-04-15', '2024-06-15', 4, 4),
(16, '2024-06-16', '2024-07-16', 5, 5),
(17, '2024-07-17', '2024-08-17', 6, 6),
(18, '2024-08-18', '2024-09-18', 7, 7),
(19, '2024-09-19', '2024-10-19', 8, 8),
(20, '2024-10-20', '2024-12-20', 9, 9),
(21, '2026-02-02', '2026-03-02', 6, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `materi`
--

CREATE TABLE `materi` (
  `id_mat` int(10) NOT NULL,
  `nama_mat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `materi`
--

INSERT INTO `materi` (`id_mat`, `nama_mat`) VALUES
(1, 'Database Administrator'),
(2, 'Android Programming with Java'),
(3, 'Machine Learning in Data Science'),
(4, 'IT Infrastructure Library Foundation'),
(5, 'IT Management Essentials'),
(6, 'AutoCAD'),
(7, 'C++ Programming'),
(8, 'Computer Network'),
(9, 'Bahasa Jerman'),
(10, 'Video Editing');

-- --------------------------------------------------------

--
-- Struktur dari tabel `peserta`
--

CREATE TABLE `peserta` (
  `id_pst` int(10) NOT NULL,
  `nama_pst` varchar(50) NOT NULL,
  `email_pst` varchar(50) NOT NULL,
  `hp_pst` varchar(13) NOT NULL,
  `instansi_pst` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `peserta`
--

INSERT INTO `peserta` (`id_pst`, `nama_pst`, `email_pst`, `hp_pst`, `instansi_pst`) VALUES
(1, 'Pepillo Layborn', 'playborn0@freewebs.com', '7653198206', 'Skiba'),
(2, 'Earvin Moseley', 'emoseley1@blogtalkradio.com', '3627424835', 'Topdrive'),
(3, 'Hayden Griffin', 'hgriffin2@webeden.co.uk', '4813146337', 'Ailane'),
(4, 'Frederique Lever', 'flever3@networkadvertising.org', '9788232050', 'Demivee'),
(5, 'Berky Bourrel', 'bbourrel4@cbc.ca', '3849168598', 'Kare'),
(6, 'Raimondo Hassell', 'rhassell5@bloglines.com', '7618127869', 'Tagpad'),
(7, 'Tybie Winkworth', 'twinkworth6@apple.com', '5429755291', 'Kazio'),
(8, 'Kellie Mulvihill', 'kmulvihill7@nasa.gov', '6246900520', 'Flipopia'),
(9, 'Joly Dossetter', 'jdossetter8@bluehost.com', '4634515742', 'Cogidoo'),
(10, 'Daron Gulk', 'dgulk9@oakley.com', '7768126904', 'Jetwire'),
(11, 'Ranique Arnout', 'rarnouta@walmart.com', '4659974197', 'Twitterbridge'),
(12, 'Aprilette MacArthur', 'amacarthurb@virginia.edu', '7654628444', 'Innojam'),
(13, 'Carine McElhargy', 'cmcelhargyc@linkedin.com', '1646764632', 'Dabtype'),
(14, 'Peirce Zipsell', 'pzipselld@4shared.com', '6699258466', 'Photobug'),
(15, 'Koral Harbord', 'kharborde@ucsd.edu', '5593368841', 'Flashspan'),
(16, 'Courtnay O\'Hara', 'coharaf@dropbox.com', '3172199958', 'Zoomzone'),
(17, 'Gianna Cowderoy', 'gcowderoyg@census.gov', '6504974037', 'Divape'),
(18, 'Saw Holburn', 'sholburnh@vinaora.com', '9894076579', 'Yamia'),
(19, 'Austin Payler', 'apayleri@un.org', '6686687897', 'Centimia'),
(20, 'Hunter Sumpter', 'hsumpterj@arstechnica.com', '5068060429', 'Gigashots'),
(21, 'Alvina Wratten', 'awrattenk@ox.ac.uk', '7211850149', 'Eabox'),
(22, 'Elie Sandcraft', 'esandcraftl@businesswire.com', '9111210192', 'Gigazoom'),
(23, 'Der Trenfield', 'dtrenfieldm@naver.com', '2783437914', 'Zoombox'),
(24, 'Jaquelyn Voice', 'jvoicen@comsenz.com', '9628874543', 'Zoonoodle'),
(25, 'Cooper Staniland', 'cstanilando@cloudflare.com', '8974615712', 'Eimbee'),
(26, 'Emmeline Boriston', 'eboristonp@washingtonpost.com', '7862055050', 'Shufflester'),
(27, 'Timmy Fauning', 'tfauningq@wikispaces.com', '8146093539', 'Babbleblab'),
(28, 'Donielle Ghidoli', 'dghidolir@google.cn', '4919988026', 'Skiptube'),
(29, 'Bobette Ilyas', 'bilyass@whitehouse.gov', '4574101689', 'Topicblab'),
(30, 'Kathi Cheal', 'kchealt@wordpress.org', '6702865215', 'Devshare'),
(31, 'Sharon Pouton', 'spoutonu@weather.com', '4369693217', 'Fivebridge'),
(32, 'Emmie Ehlerding', 'eehlerdingv@skyrock.com', '5781494938', 'Brainverse'),
(33, 'Janaya Twelvetrees', 'jtwelvetreesw@plala.or.jp', '5903595712', 'Oyondu'),
(34, 'Marleah Andresen', 'mandresenx@google.com.br', '9357449163', 'Ntags'),
(35, 'Menard Lodovichi', 'mlodovichiy@eepurl.com', '1207342684', 'Voolith'),
(36, 'Crystal Binford', 'cbinfordz@vk.com', '6206191411', 'Rhybox'),
(37, 'Harriet Callar', 'hcallar10@disqus.com', '2517249395', 'Skilith'),
(38, 'Marylou Woodworth', 'mwoodworth11@google.ca', '5335105875', 'Skynoodle'),
(39, 'Sidnee Satteford', 'ssatteford12@qq.com', '4643625321', 'Omba'),
(40, 'Andras Khilkov', 'akhilkov13@fastcompany.com', '3778549812', 'Devshare'),
(41, 'Karalee Francesco', 'kfrancesco14@privacy.gov.au', '9884401792', 'Izio'),
(42, 'Benton Handford', 'bhandford15@adobe.com', '9756411309', 'Geba'),
(43, 'Priscilla Ashmole', 'pashmole16@va.gov', '1357774712', 'Gabvine'),
(44, 'Donella Trever', 'dtrever17@facebook.com', '9918096049', 'Mynte'),
(45, 'Fair Cobbled', 'fcobbled18@youku.com', '7826831865', 'Chatterpoint'),
(46, 'Welbie Anstice', 'wanstice19@china.com.cn', '6816661855', 'Zooxo'),
(47, 'Amara Gregoli', 'agregoli1a@de.vu', '9945967889', 'Rhynyx'),
(48, 'Annnora Rubbens', 'arubbens1b@forbes.com', '3408318589', 'Browsecat'),
(49, 'Olvan Morison', 'omorison1c@gizmodo.com', '2345987244', 'Devbug'),
(50, 'Pauline Melding', 'pmelding1d@wikimedia.org', '4977132265', 'Skimia'),
(51, 'Devonne Reilinger', 'dreilinger1e@weebly.com', '6829971498', 'Mynte'),
(52, 'Moina Gennings', 'mgennings1f@prlog.org', '5121672779', 'Kaymbo'),
(53, 'Max Stockill', 'mstockill1g@prweb.com', '4502939913', 'Yamia'),
(54, 'Poppy Milch', 'pmilch1h@ucoz.ru', '1574276144', 'Thoughtsphere'),
(55, 'Tiena Grabban', 'tgrabban1i@slideshare.net', '5447679638', 'Twitterworks'),
(56, 'Worthington MacFaell', 'wmacfaell1j@accuweather.com', '2873119588', 'Kayveo'),
(57, 'Bessy Prentice', 'bprentice1k@tinyurl.com', '2067482845', 'Zava'),
(58, 'Irma Bourthoumieux', 'ibourthoumieux1l@youtu.be', '2936371110', 'Fivespan'),
(59, 'Chad Lahive', 'clahive1m@naver.com', '7277862128', 'Wordware'),
(60, 'Major Malthouse', 'mmalthouse1n@statcounter.com', '5695302418', 'Realpoint'),
(61, 'Carolyne Hanrahan', 'chanrahan1o@nasa.gov', '2334633774', 'Topiczoom'),
(62, 'Layney Rustan', 'lrustan1p@pinterest.com', '3133894886', 'Divanoodle'),
(63, 'Shannen Gingles', 'sgingles1q@bluehost.com', '9892757709', 'Devbug'),
(64, 'Fairfax Mechem', 'fmechem1r@dailymail.co.uk', '2741439357', 'Youspan'),
(65, 'Richardo Kalkhoven', 'rkalkhoven1s@dyndns.org', '4889451714', 'Voonder'),
(66, 'Mallory Winnett', 'mwinnett1t@fc2.com', '6404426462', 'Shufflebeat'),
(67, 'Gabbie Gurery', 'ggurery1u@bravesites.com', '8395797449', 'Flipstorm'),
(68, 'Nester Matts', 'nmatts1v@cam.ac.uk', '2856315113', 'Jabbersphere'),
(69, 'Reina Hubber', 'rhubber1w@dell.com', '6676856078', 'Blogspan'),
(70, 'Dunc Roebuck', 'droebuck1x@netscape.com', '2598987353', 'Eabox'),
(71, 'Saundra Mattiato', 'smattiato1y@omniture.com', '4683774395', 'Agivu'),
(72, 'Merci Anning', 'manning1z@reddit.com', '4863392050', 'Mycat'),
(73, 'Rivalee Choake', 'rchoake20@de.vu', '4298031096', 'Jaxnation'),
(74, 'Ame Dowzell', 'adowzell21@vinaora.com', '3828083640', 'Trudeo'),
(75, 'Ulrikaumeko Longmead', 'ulongmead22@friendfeed.com', '9748508389', 'Ozu'),
(76, 'Nert Rosborough', 'nrosborough23@purevolume.com', '7089762390', 'Quaxo'),
(77, 'Yorker McCaskill', 'ymccaskill24@vkontakte.ru', '5291345554', 'Skippad'),
(78, 'Bard Sebring', 'bsebring25@youtu.be', '7226477146', 'Thoughtbridge'),
(79, 'Tyrone Drever', 'tdrever26@oracle.com', '1501040802', 'BlogXS'),
(80, 'Anders Bernardoni', 'abernardoni27@salon.com', '5316848456', 'Flipbug'),
(81, 'Adan Whitfield', 'awhitfield28@stanford.edu', '1456587131', 'Quamba'),
(82, 'Beulah Brenard', 'bbrenard29@delicious.com', '5208217283', 'Viva'),
(83, 'Karry Whyborn', 'kwhyborn2a@topsy.com', '9332022493', 'Rhyloo'),
(84, 'Pippa Willman', 'pwillman2b@google.it', '7104308903', 'Latz'),
(85, 'Goldia Perrottet', 'gperrottet2c@phpbb.com', '2147871080', 'Jaxnation'),
(86, 'Daria Broadfoot', 'dbroadfoot2d@paginegialle.it', '5235436345', 'Rhynoodle'),
(87, 'Christiane Rubel', 'crubel2e@altervista.org', '3334722888', 'Camido'),
(88, 'Dwayne Gready', 'dgready2f@mysql.com', '5461434664', 'Blognation'),
(89, 'Nelson Ferryn', 'nferryn2g@cargocollective.com', '5013712501', 'Zoonder'),
(90, 'Meyer Camerati', 'mcamerati2h@intel.com', '8727670562', 'Demizz'),
(91, 'Stacee Drillot', 'sdrillot2i@purevolume.com', '3087674635', 'Divanoodle'),
(92, 'Jeramey Higginbottam', 'jhigginbottam2j@wiley.com', '7604918155', 'Tazzy'),
(93, 'Conny Isakov', 'cisakov2k@addthis.com', '4078368453', 'Camimbo'),
(94, 'Sheri Kochl', 'skochl2l@illinois.edu', '6145118714', 'Snaptags'),
(95, 'Fowler Ranyell', 'franyell2m@google.ca', '8443642679', 'Bluejam'),
(96, 'Drona Fearneley', 'dfearneley2n@cbslocal.com', '9685311405', 'Shufflester'),
(97, 'Dov Hornbuckle', 'dhornbuckle2o@unicef.org', '2751951755', 'Meembee'),
(98, 'Dedra Braven', 'dbraven2p@pagesperso-orange.fr', '1161717367', 'Chatterpoint'),
(99, 'Reuven Alleway', 'ralleway2q@pen.io', '2284249168', 'Oyope'),
(100, 'Lyn Swindell', 'lswindell2r@berkeley.edu', '5584834137', 'Abata');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_kelas`
--
ALTER TABLE `detail_kelas`
  ADD PRIMARY KEY (`id_detail_kls`),
  ADD KEY `id_kls` (`id_kls`),
  ADD KEY `id_pst` (`id_pst`);

--
-- Indeks untuk tabel `instruktur`
--
ALTER TABLE `instruktur`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indeks untuk tabel `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id_kls`),
  ADD KEY `id_ins` (`id_ins`),
  ADD KEY `id_mat` (`id_mat`);

--
-- Indeks untuk tabel `materi`
--
ALTER TABLE `materi`
  ADD PRIMARY KEY (`id_mat`);

--
-- Indeks untuk tabel `peserta`
--
ALTER TABLE `peserta`
  ADD PRIMARY KEY (`id_pst`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_kelas`
--
ALTER TABLE `detail_kelas`
  MODIFY `id_detail_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- AUTO_INCREMENT untuk tabel `instruktur`
--
ALTER TABLE `instruktur`
  MODIFY `id_ins` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT untuk tabel `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT untuk tabel `materi`
--
ALTER TABLE `materi`
  MODIFY `id_mat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `peserta`
--
ALTER TABLE `peserta`
  MODIFY `id_pst` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_kelas`
--
ALTER TABLE `detail_kelas`
  ADD CONSTRAINT `detail_kelas_ibfk_1` FOREIGN KEY (`id_kls`) REFERENCES `kelas` (`id_kls`),
  ADD CONSTRAINT `detail_kelas_ibfk_2` FOREIGN KEY (`id_pst`) REFERENCES `peserta` (`id_pst`);

--
-- Ketidakleluasaan untuk tabel `kelas`
--
ALTER TABLE `kelas`
  ADD CONSTRAINT `kelas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `instruktur` (`id_ins`),
  ADD CONSTRAINT `kelas_ibfk_2` FOREIGN KEY (`id_mat`) REFERENCES `materi` (`id_mat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
