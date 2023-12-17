CREATE DATABASE  IF NOT EXISTS `todo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `todo`;
DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT  IGNORE INTO `task` VALUES (1,'manual sunrise',1),(2,'feed crocs',2),(3,'learn java',0),(4,'
accomplish a feat',1),(5,'cook borsch',2),(6,'play with kid',0),(7,'
write a todo list',1),(8,'laugh',2),(9,'learn java',0),(10,'make The Roman Empire great again',1),(11,'lava serfing',2),(12,'this and that',0),(13,'eat dinner',1),(14,'buy a gift',2),(15,'manual sunset',0);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;