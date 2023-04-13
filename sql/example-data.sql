-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Database to use
--

USE hotel;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES 
(null,'manager','Rostyslav ','Ivanyshyn','rosivanyshyn@gmail.com','123456',1),
(null,'user','Oleg','Krivtsov','olegkrivtsov@gmai.com','234567',1),
(null,'user','Mikhail','Shcherbyakov','mikhailshcherbyakov@gmail.com','qwerty',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `apartment`
--

LOCK TABLES `apartment` WRITE;
/*!40000 ALTER TABLE `apartment` DISABLE KEYS */;
INSERT INTO `apartment` VALUES 
(1,'Затишний хостел','У кожному номері хостелу є письмовий стіл, телевізор із плоским екраном, спільна ванна кімната та постільна білизна. В усіх варіантах розміщення є шафа для одягу.','111','7 Naberezhno-Lugova Street, Киев, 02000, Украина',2,'1','хостел',280,1),
(2,'DREAM Hostel Kyiv','Хостел DREAM Kyiv із рестораном, власною парковкою, баром і садом розташований у Києві, за 400 м від Андріївського узвозу. До послуг гостей. Помешкання розміщене менш ніж за 1 км від Михайлівського Золотоверхого монастиря, за 13 хвилин пішки від Софійського собору та за 2 км від Володимирського собору. До послуг гостей сучасні номери, кафе, бар, цілодобова стійка реєстрації, трансфер з/до аеропорту, спільна кухня та зони загального користування для роботи й відпочинку з безкоштовним WI-FI.','112','Andreevskiy Spusk 2D, Київ, 04070, Україна',4,'5','хостел',500,1),
(3,'Готель Надія','Nunc dapibus porta eros vel aliquam. Morbi lorem nisi, vestibulum at tincidunt in, ultrices vitae magna. Nulla at urna porttitor, placerat ipsum ultrices, commodo massa. Suspendisse tempus vitae neque vitae ornare. Nullam sed arcu dui. Sed eu ipsum ut erat interdum fringilla.','113','7 Naberezhno-Lugova Street, Киев, 02000, Украина',5,'1','готель',280,1),
(4,'Одеський готель',' Suspendisse tempus vitae neque vitae ornare. Nullam sed arcu dui. Sed eu ipsum ut erat interdum fringilla','114','Andreevskiy Spusk 2D, Київ, 04070, Україна',2,'6','готель',450,1),
(5,'Апартаменти','В одеському Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris et quam sit amet magna fringilla iaculis. Vestibulum ac augue nibh. Vestibulum id elit a ipsum facilisis dignissim','115','7 Naberezhno-Lugova Street, Киев, 02000, Украина',6,'1','апартаменти',280,1),
(6,'Апартаменти2','Nulla pretium ante id pretium vehicula. Duis tincidunt neque ut rutrum aliquam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras turpis tortor, feugiat at fringilla ac, blandit in libero. Vivamus cursus, enim quis luctus egestas, massa velit hendrerit quam, id fermentum ligula libero maximus dui. Vivamus ac eros nec velit convallis auctor. Aliquam accumsan porta risus, ac dapibus ipsum semper sit amet.','116','Andreevskiy Spusk 2D, Київ, 04070, Україна',2,'3','апартаменти',500,1),
(7,'Апартаменти3','Phasellus a egestas erat. Integer sapien leo, iaculis quis velit sed, ullamcorper blandit quam. Maecenas a fermentum velit. Vivamus enim metus, volutpat ac commodo id, varius at est. In venenatis rutrum risus, nec tincidunt leo viverra a. Integer tristique nulla a neque egestas dapibus. Maecenas auctor porttitor ipsum. Quisque dui dui, aliquet porta aliquet vel, imperdiet et dui. Aenean vestibulum dolor dui, ac suscipit odio egestas non. ','117','7 Naberezhno-Lugova Street, Киев, 02000, Украина',2,'1','апартаменти',555,0),
(8,'Зелена Таверна','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris et quam sit amet magna fringilla iaculis. Vestibulum ac augue nibh. Vestibulum id elit a ipsum facilisis dignissim','118','Andreevskiy Spusk 2D, Київ, 04070, Україна',2,'1','таверна',800,1),
(9,'Авеню-хостел','Donec vulputate vitae ante non fringilla. Pellentesque dui nunc, tristique et tellus vitae, ornare ultrices nulla. Suspendisse congue lorem sit amet dictum rhoncus. In efficitur mi sit amet venenatis elementum','119','7 Naberezhno-Lugova Street, Киев, 02000, Украина',7,'4','хостел',280,1),
(10,'Bukovel VIP Residence','Цей апарт-готель розташований посеред Карпатських гір на гірськолижному курорті Буковель, приблизно за 20 км від Яблуницького перевалу. Він пропонує до уваги гостей ресторан європейської кухні, чудовий спа-центр і нічний клуб з ді-джеєм.','120','Polyanitsya village, TK Bukovel, Буковель, 78593, Україна',2,'1','апарт-готель',5000,1),
(11,'Bukovel Hotel','Цей 3-зірковий готель розміщений у центрі Буковеля. Його оточують численні гірськолижні траси загальною протяжністю 53 км. До послуг гостей сучасний спа-центр, різноманітні зручності для відпочинку, ресторан інтернаціональної кухні та безкоштовний Wi-Fi.','121','Буковель, Буковель, 78593, Україна ',2,'1','готель',4000,1),
(12,'Затишний Котедж','Будинок для відпочинку \"Затишний котедж\" розташований у селі Яблуниця. До послуг гостей сад і приладдя для барбекю. Відстань до селища Ворохта становить 15 км. До послуг гостей безкоштовний Wi-Fi та безкоштовна власна парковка на території помешкання. На території помешкання можна взяти напрокат лижне спорядження та велосипеди, а в його околицях – зайнятися лижним спортом і пішим туризмом.','122','Н09 буд.√67, Яблуниця, 78592, Україна ',4,'2','котедж',6440,1);
/*!40000 ALTER TABLE `apartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES 
(null,2,CURRENT_DATE + INTERVAL 1 DAY, CURRENT_DATE + INTERVAL 4 DAY,0,'2023-01-17 17:02:30',1,1),
(null,4,CURRENT_DATE - INTERVAL 2 DAY, CURRENT_DATE + INTERVAL 1 DAY,0,'2023-01-17 17:02:30',1,1),
(null,1,CURRENT_DATE + INTERVAL 6 DAY, CURRENT_DATE + INTERVAL 9 DAY,0,'2023-01-17 17:02:30',1,1),
(null,6,CURRENT_DATE - INTERVAL 4 DAY, CURRENT_DATE - INTERVAL 2 DAY,0,'2023-01-17 17:02:30',1,1),
(null,8,CURRENT_DATE, CURRENT_DATE + INTERVAL 1 DAY,0,'2023-01-27 09:06:53',18,1),
(null,10,CURRENT_DATE, CURRENT_DATE + INTERVAL 2 DAY,1,'2023-01-28 10:41:02',1,2),
(null,4,CURRENT_DATE+ INTERVAL 6 DAY, CURRENT_DATE + INTERVAL 9 DAY,1,'2023-01-29 11:52:20',1,2),
(null,7,'2023-02-08','2023-02-09',1,'2023-02-08 20:19:39',20,1),
(null,8,CURRENT_DATE + INTERVAL 9 DAY, CURRENT_DATE + INTERVAL 15 DAY,1,'2023-02-08 20:19:44',20,1),
(null,8,CURRENT_DATE + INTERVAL 25 DAY, CURRENT_DATE + INTERVAL 32 DAY,1,'2023-02-08 20:19:54',20,1),
(null,8,CURRENT_DATE + INTERVAL 38 DAY, CURRENT_DATE + INTERVAL 46 DAY,1,'2023-02-08 20:19:54',20,1),
(null,5,'2023-02-20','2023-02-22',1,'2023-02-25 12:50:58',1,4),
(null,6,'2023-02-28','2023-03-03',1,'2023-02-27 15:04:58',1,4);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (27,2,'2','апартаменти',6000,'Апартаменти із садом','2023-01-20','2023-01-18',1,9),(31,12,'2','хостел',2200,'Можливість забронювати 2 кімнати по 6 чоловік в кожній','2023-01-04','2023-01-04',1,NULL),(32,1,'1','хостел',235,'Хостел в центрі міста','2023-01-04','2023-01-04',1,13),(38,4,'2','-',600,'Дві кімнати для гостей з парижу поблизу вулиці Набережна','2023-01-24','2023-01-26',1,15),(50,4,'2','апартаменти',8000,'Курортні апартаменти в горах','2023-02-24','2023-02-08',20,NULL),(116,2,'2','котедж',6000,'Котедж поряд з лісом','2023-02-24','2023-02-24',20,NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `response_to_order`
--

LOCK TABLES `response_to_order` WRITE;
/*!40000 ALTER TABLE `response_to_order` DISABLE KEYS */;
INSERT INTO `response_to_order` VALUES (9,'Апартаменти із невеличким садом поруч'),(13,''),(15,'');
/*!40000 ALTER TABLE `response_to_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `response_to_order_has_apartment`
--

LOCK TABLES `response_to_order_has_apartment` WRITE;
/*!40000 ALTER TABLE `response_to_order_has_apartment` DISABLE KEYS */;
INSERT INTO `response_to_order_has_apartment` VALUES (13,1),(15,1),(15,3),(9,5),(9,6);
/*!40000 ALTER TABLE `response_to_order_has_apartment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-13 15:23:54
