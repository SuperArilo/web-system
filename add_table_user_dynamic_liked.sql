/*
 Navicat Premium Data Transfer

 Source Server         : localhost57
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3357
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 21/12/2021 11:11:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for super_note
-- ----------------------------
DROP TABLE IF EXISTS `super_note`;
CREATE TABLE `super_note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `synopsis` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createtime` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `commentsum` int(11) NULL DEFAULT NULL,
  `watchsum` int(11) NULL DEFAULT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of super_note
-- ----------------------------
INSERT INTO `super_note` VALUES (4, '测试3', '简介3', '2021-10-14', 0, 9, '<h1>欢迎使用在线编辑器 Markdown Editor</h1>\n<p><strong>Markdown</strong>是一种简单的文本格式语言，以简单的键盘字符编写文档，转换成html后就能显示复杂的页面效果。</p>\n<p><img src=\"./assets/images/index/markdown.png\" alt=\"markdown\" title=\"markdown\" /></p>\n<h2>markdownEditor.cn是一个在线的Markdown文本编辑器</h2>\n<p>markdownEditor.cn是一个在线的Markdown文本编辑器，基于Vue开源项目mavonEditor开发(Git:<a href=\"https://github.com/hinesboy/mavonEditor\" title=\"MavonEditer\">MavonEditer</a>)，扩展了HTML复制、html文件导出、md文件导出等功能。</p>\n<h3>开源项目mavonEditor</h3>\n<p>开源项目mavonEditor依赖<a href=\"https://github.com/markdown-it/markdown-it\">markdown-it</a>,相应的继承了<strong>markdown-it</strong>的各种扩展，如语法高亮、表格（Tables）、删除线(Strikethrough)、下标(subscript)、上标(superscript)、emoji(emoji)等等。</p>\n<h2>markdownEditor.cn 中的Markdown语法示例</h2>\n<p>@<a href=\"目录\">toc</a></p>\n<hr />\n<h3>字符效果</h3>\n<p>~~删除线~~   ~~中划线~~</p>\n<p><strong>粗体</strong>  <strong>粗体</strong></p>\n<p><em>斜体字</em>      <em>斜体字</em></p>\n<p><em><strong>粗斜体</strong></em> <em><strong>粗斜体</strong></em></p>\n<p>上标：X^2^，下标：O~2~</p>\n<p>++下划线++  会转化为 <code>下划线</code></p>\n<p>==标记==  会转化为 <code>marked</code></p>\n<p>abbr缩写</p>\n<p>*[HTML]: Hyper Text Markup Language\n*[W3C]:  World Wide Web Consortium\nHTML 规范由 W3C 维护</p>\n<hr />\n<h3>分割线</h3>\n<hr />\n<hr />\n<hr />\n<hr />\n<h3>标题</h3>\n<h1>一级标题</h1>\n<h1>一级标题</h1>\n<h1>一级标题</h1>\n<h2>二级标题</h2>\n<h2>二级标题</h2>\n<h2>二级标题</h2>\n<h3>三级标题</h3>\n<h3>三级标题</h3>\n<h4>四级标题</h4>\n<h4>四级标题</h4>\n<h5>五级标题</h5>\n<h5>五级标题</h5>\n<h6>六级标题</h6>\n<h6>六级标题</h6>\n<hr />\n<h3>列表</h3>\n<h4>有序列表</h4>\n<ol>\n<li>第一件事情</li>\n<li>第二件事情</li>\n<li>第三件工作</li>\n</ol>\n<h4>无序列表</h4>\n<ul>\n<li>无序markdown</li>\n<li>无序markdown</li>\n<li>无序markdown</li>\n</ul>\n<ul>\n<li>列表一</li>\n<li>列表二</li>\n<li>列表三</li>\n</ul>\n<ul>\n<li>无序markdown</li>\n<li>无序markdown\n<ul>\n<li>无序markdown-1</li>\n<li>无序markdown-2</li>\n<li>无序markdown-3</li>\n</ul>\n</li>\n<li>无序markdown\n<ul>\n<li>无序markdown</li>\n<li>无序markdown</li>\n<li>无序markdown</li>\n</ul>\n</li>\n<li>无序列表\n<ol>\n<li>有序列表</li>\n<li>有序列表</li>\n<li>有序列表</li>\n</ol>\n</li>\n</ul>\n<hr />\n<h3>任务列表</h3>\n<ul>\n<li>[x] 已完成任务</li>\n<li>[ ] 未完成任务\n<ul>\n<li>[ ] 下级任务</li>\n<li>[ ] 下级任务\n<ul>\n<li>[ ] 下下级任务</li>\n</ul>\n</li>\n</ul>\n</li>\n<li>[x] 已完成任务</li>\n<li>[ ] 未完成任务</li>\n</ul>\n<hr />\n<h3>链接及图片</h3>\n<p><a href=\"www.markdowneditor.cn\">链接</a></p>\n<p><a href=\"https://www.markdowneditor.cn/\" title=\"链接带标题\">链接带标题</a></p>\n<p><a href=\"mailto:nobody.test@qq.com\">mailto:nobody.test@qq.com</a></p>\n<p>直接链接：</p>\n<p><img src=\"/assets/images/index/markdown.png\" alt=\"图片描述\" /></p>\n<hr />\n<h3>代码</h3>\n<h4>代码块</h4>\n<pre><code class=\"language-type\">\n  代码段落\n\n</code></pre>\n<pre><code class=\"language-c\">int main()\n{\n    printf(&quot;hello world!&quot;);\n}\n</code></pre>\n<h4>缩进代码块</h4>\n<p>缩进四个空格，也能实现代码块的公共能</p>\n<pre><code>function(){\n    //todo something\n}\n</code></pre>\n<p>其他非代码内容</p>\n<pre><code>Markdown is a simple way to format text that looks great on any device.\nIt doesn’t do anything fancy like change the font size, color,\nor type — just the essentials, using keyboard symbols you already know.\n</code></pre>\n<h4>行内代码 <code>code</code></h4>\n<p>通过命令<code>npm install markdown-it --save</code>安装 markdown-it</p>\n<hr />\n<h3>表格(table)</h3>\n<p>| 第一排  | 第二排 |\n| ------------- | ------------- |\n| 1 Cell  | 2 Cell  |\n| 1 Cell  | 2 Cell  |</p>\n<p>第一排  | 第二排\n------------- | -------------\n1 Cell  | 2 Cell\n1 Cell  | 2 Cell</p>\n<p>| 标题1 | 标题2 | 标题3 |\n| :--  | :---: | ----: |\n| 左对齐 | 居中 | 右对齐 |\n| ----------------- | ---------------- | -------------- |</p>\n<hr />\n<h3>脚注(footnote)</h3>\n<p>见底部脚注<a href=\"一个注脚\">^hello</a></p>\n<hr />\n<h3>表情(emoji)</h3>\n<p><a href=\"https://www.webpagefx.com/tools/emoji-cheat-sheet/\">参考网站: https://www.webpagefx.com/tools/emoji-cheat-sheet/</a></p>\n<p>:laughing:</p>\n<p>:blush:</p>\n<p>:smiley:</p>\n<p>:)</p>\n<hr />\n<h3>$\\KaTeX$公式</h3>\n<p>$$E=mc^2$$</p>\n<p>$$x &gt; y$$</p>\n<p>我们可以渲染公式例如：$x_i + y_i = z_i$和$\\sum_{i=1}^n a_i=0$\n我们也可以单行渲染</p>\n<p>$$\\sum_{i=1}^n a_i=0$$</p>\n<p>$$(\\sqrt{3x-1}+(1+x)^2)$$</p>\n<p>$$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$$</p>\n<p>具体可参照<a href=\"http://www.intmath.com/cg5/katex-mathjax-comparison.php\">katex文档</a>和<a href=\"https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX\">katex支持的函数</a>以及<a href=\"https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference\">latex文档</a></p>\n<hr />\n<h3>布局</h3>\n<p>::: hljs-left\n居左\n:::</p>\n<p>::: hljs-center\n居中\n:::</p>\n<p>::: hljs-right\n居右\n:::</p>\n<hr />\n<h3>定义</h3>\n<p>术语一</p>\n<p>:   定义一</p>\n<p>包含有<em>行内标记</em>的术语二</p>\n<p>:   定义二</p>\n<pre><code>    {一些定义二的文字或代码}\n\n定义二的第三段\n</code></pre>\n<hr />\n<h3>引用 Blockquotes</h3>\n<blockquote>\n<p>引用单号文本 Blockquotes</p>\n</blockquote>\n<blockquote>\n<p>引用多行文本\n引用多行文本</p>\n</blockquote>\n<p>多级样式</p>\n<blockquote>\n<p>第一级引用格式</p>\n<blockquote>\n<p>第二级引用\n第二级引用</p>\n<blockquote>\n<p>第三级</p>\n</blockquote>\n</blockquote>\n<p>第一级引用</p>\n</blockquote>\n<hr />\n<h3>HTM实体符号</h3>\n<p>18ºC  &quot;  \'\nX² Y³ ¾ ¼  ×  ÷   »</p>\n<p>&amp; &lt; &gt; ¥ € ® ± ¶ § ¦ ¯ « ·\n© &amp;  ¨ ™ ¡ £</p>\n<p>结束</p>\n<hr />\n');

-- ----------------------------
-- Table structure for user_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `user_dynamic`;
CREATE TABLE `user_dynamic`  (
  `dynamicid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `createtime` datetime(0) NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `likesum` int(11) NOT NULL,
  `commentsum` int(11) NOT NULL,
  `watchsum` int(11) NOT NULL,
  PRIMARY KEY (`dynamicid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dynamic
-- ----------------------------
INSERT INTO `user_dynamic` VALUES (1, 19, '2021-11-11 10:43:34', '测试', 0, 0, 0);

-- ----------------------------
-- Table structure for user_dynamic_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_dynamic_comment`;
CREATE TABLE `user_dynamic_comment`  (
  `commentid` int(11) NOT NULL AUTO_INCREMENT,
  `dynamicid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createtime` datetime(0) NOT NULL,
  `likesum` int(11) NOT NULL,
  `unlikesum` int(11) NOT NULL,
  PRIMARY KEY (`commentid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dynamic_comment
-- ----------------------------
INSERT INTO `user_dynamic_comment` VALUES (1, 1, 10, '111111', '2021-11-11 09:59:55', 11, 11);
INSERT INTO `user_dynamic_comment` VALUES (2, 1, 11, '22222', '2021-11-11 10:00:06', 2222, 2222);
INSERT INTO `user_dynamic_comment` VALUES (3, 1, 19, '333333', '2021-11-11 11:36:06', 333, 333);

-- ----------------------------
-- Table structure for user_dynamic_image
-- ----------------------------
DROP TABLE IF EXISTS `user_dynamic_image`;
CREATE TABLE `user_dynamic_image`  (
  `imageid` int(11) NOT NULL AUTO_INCREMENT,
  `dynamicid` int(11) NOT NULL,
  `imagename` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imageurl` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`imageid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dynamic_image
-- ----------------------------
INSERT INTO `user_dynamic_image` VALUES (1, 1, '6c7bfb4cf8784c75a2e8b22ce66db270.jpg', 'http://localhost/image/dynamic/6c7bfb4cf8784c75a2e8b22ce66db270.jpg');
INSERT INTO `user_dynamic_image` VALUES (2, 1, '26e4944bd12a4ae585207ddf54b7d3db.jpg', 'http://localhost/image/dynamic/26e4944bd12a4ae585207ddf54b7d3db.jpg');

-- ----------------------------
-- Table structure for user_dynamic_liked
-- ----------------------------
DROP TABLE IF EXISTS `user_dynamic_liked`;
CREATE TABLE `user_dynamic_liked`  (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `dynamicId` int(11) NOT NULL COMMENT '该用户喜欢的动态id',
  `likedTimePoints` datetime(0) NULL DEFAULT NULL COMMENT '喜欢的时间点'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dynamic_liked
-- ----------------------------
INSERT INTO `user_dynamic_liked` VALUES (19, 1, '2021-12-21 10:57:01');
INSERT INTO `user_dynamic_liked` VALUES (10, 1, '2021-12-21 10:57:25');
INSERT INTO `user_dynamic_liked` VALUES (9, 1, '2021-12-21 10:57:33');
INSERT INTO `user_dynamic_liked` VALUES (12, 1, '2021-12-21 10:57:50');

-- ----------------------------
-- Table structure for user_information
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `uid` int(32) NOT NULL,
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userpwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `class` int(11) NOT NULL,
  `userhead` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `imagename` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registertime` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES (10, 19, 'SuperArilo', '406713a4be0aea3f2a01f74ba751f442', 'superarilo@189.cn', 1, 'http://localhost/image/userhead/542fe18b5f764f68aaf5a1ea97166fe8.jpg', '542fe18b5f764f68aaf5a1ea97166fe8.jpg', '2021-09-16 16:03:16');
INSERT INTO `user_information` VALUES (11, 11, '111', '11111', '11111', 1, '11111', '111111', '2021-11-01 12:00:31');
INSERT INTO `user_information` VALUES (19, 10, 'test', 'tadwa', 'wadwa', 0, 'awdawd', 'adawd', '2021-09-26 15:22:53');

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES (11, 10, '11111', '2021-09-27 15:51:13');
INSERT INTO `user_message` VALUES (12, 10, '11111', '2021-09-27 15:51:15');
INSERT INTO `user_message` VALUES (13, 10, '11111', '2021-09-27 15:51:15');
INSERT INTO `user_message` VALUES (14, 10, '11111', '2021-09-27 15:51:16');
INSERT INTO `user_message` VALUES (15, 10, '11111', '2021-09-27 15:51:17');
INSERT INTO `user_message` VALUES (16, 10, '11111', '2021-09-27 15:51:17');
INSERT INTO `user_message` VALUES (17, 10, '11111', '2021-09-27 15:51:18');
INSERT INTO `user_message` VALUES (18, 10, '11111', '2021-09-27 15:51:19');

-- ----------------------------
-- Table structure for user_security_issues
-- ----------------------------
DROP TABLE IF EXISTS `user_security_issues`;
CREATE TABLE `user_security_issues`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `question` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `answer` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for up_note
-- ----------------------------
DROP PROCEDURE IF EXISTS `up_note`;
delimiter ;;
CREATE PROCEDURE `up_note`(in note_id int)
BEGIN
	UPDATE super_note SET watchsum = watchsum + 1 WHERE id = note_id;
	SELECT id,createtime,commentsum,watchsum,content FROM super_note WHERE id = note_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
