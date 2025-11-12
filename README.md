# MochibeSupport
情報化社会において、確かな知識と技術を身に着けた人材を育成するための職業訓練校。
その過程において、生徒と講師、生徒とキャリアコンサルタントとの連絡や交流を円滑にし、生徒のモチベーション維持をサポートする目的。
完全クローズド制サイト。

【機能の概要】
1. 共通  
  1.1. ログイン  
    ・ログイン ID とパスワードを入力し、ユーザー認証を行う。  
    ・管理者は開発者から、生徒及び講師、キャリアコンサルタントは管理者から  
      ログイン ID とパスワードが配布される。ユーザー情報が登録されるとパスワードはハッシュ化してデータベースに保存される。  
2. アカウント管理  
  2.1. アカウント一覧  
    ・登録したユーザー情報を一覧する。  
  2.2. アカウント登録  
    ・生徒の情報(ログイン ID、パスワード、所属クラス、氏名、修了日、退校日)と職員の情報(ログイン ID、パスワード、担当クラス、氏名、職種)を登録する。  
  2.3. アカウント削除  
    ・完全クローズドサイトのため、修了した生徒または、退職した職員の個人情報を削除する。  
3. ユーザー編集   
  3.1. ユーザー登録  
    ・生徒、職員は初回ログイン時にパスワード、生年月日、性別、電話番号、メールアドレス、住所を登録する。  
  3.2. ユーザー変更  
    ・登録した個人情報を変更する(ID、氏名は変更不可)。  
  3.3. ユーザー確認  
    ・登録及び変更が完了後のユーザー情報を確認する。  
4. 質問  
  4.1. 質問登録  
    ・講師に対して質問を投稿できる(文字数２００文字以下)。  
  4.2. 質問一覧  
    ・生徒からの質問を講師は閲覧できる。質問を回答した場合は回答済チェックボタンを押すと、質問を削除できる。  
5. お知らせ  
  5.1. お知らせ一覧  
    ・生徒は講師からのお知らせを一覧できる。  
  5.2. お知らせ登録  
    ・休校日やテストの日程などのお知らせを登録する。  
  5.3. お知らせ削除  
    ・講師はお知らせ一覧にて削除できる。  
6. 書籍貸出管理  
  6.1. 書籍貸出一覧  
    ・管理者より登録された貸出本の一覧を表示する。  
  6.2. 書籍登録  
    ・貸し出す書籍の表題氏、本の写真を登録する。  
  6.3. 貸出書籍返却  
    ・管理者は、生徒が借りた本が返却された場合は貸出一覧の返却済ボタンをクリックし、処理を行う。  
7. おすすめ情報  
  7.1. おすすめ情報一覧  
    ・講師からのおすすめの参考書や、サイトの情報を一覧でき、クリックするとそのサイト(参考書の場合は Amazon など)に移動する。  
  7.2. おすすめ情報登録  
    ・参考書やサイトのタイトル、リンク、写真を登録する。  
  7.3. おすすめ情報削除  
    ・管理者は一覧に削除用のチェックボタンがあり、それをクリックすると削除できる。  
8. 面談予約  
  8.1. 面談スケジュール編集  
    ・キャリアコンサルタントは面談可能な日時を表示されているカレンダーから 3 週間分選択する。選択はチェックボックスで行い、チェックをつけたところは面談可能となる。  
  8.2. 面談一覧  
    ・生徒と面談予約の予定を表示する。  
  8.3. 面談予約登録  
    ・キャリアコンサルタントの空いている日時より、面談の予約を行う。  
    ・面談予約が確定している場合は、予定している日時を表示する。  
  8.4. 面談キャンセル  
    ・生徒からキャンセルの申し出がある場合は、面談一覧画面に表示されているチェックボタンを選択し予約の取り消しを行う。

【データベース初期設定】
PostgreSQL推奨。

1.以下のSQLを実行してください。 CREATE DATABASE motibe_support;

2.データベースに接続し、以下のSQLを実行してください。 
CREATE TABLE users(id SERIAL ,email_address TEXT NOT NULL ,password TEXT NOT NULL ,class_code VARCHAR(5) ,name VARCHAR(20) NOT NULL ,birthday DATE ,gender VARCHAR(2) ,job_category_code VARCHAR(2), telephone_number TEXT ,day_of_leaving DATE ,primary key(id) );


CREATE TABLE lending_library(id SERIAL
 ,book_name VARCHAR(30) NOT NULL ,reading_name VARCHAR(20) ,rental_date DATE ,return_date DATE ,days_in_arrears INTEGER ,primary key(id) );

CREATE TABLE photo_of_book(id SERIAL ,file_key CHAR(32) NOT NULL ,file_path VARCHAR(100) ,content_type VARCHAR(50) ,primary key(id)
 );

CREATE TABLE schedule(teacher_name VARCHAR(20) NOT NULL ,date DATE NOT NULL ,start_time VARCHAR(5) NOT NULL ,end_time VARCHAR(5) NOT NULL ,mark VARCHAR(2) NOT NULL );

CREATE TABLE url_link(introduction_name VARCHAR(50) NOT NULL ,link VARCHAR(65535) NOT NULL ,file_key CHAR(32)
 ,file_path VARCHAR(100) ,content_type VARCHAR(50) ,primary key(introduction_name, link) );


CREATE TABLE question(student_name VARCHAR(20) NOT NULL ,content VARCHAR(100) NOT NULL );

CREATE TABLE news(teacher_name VARCHAR(20) NOT NULL ,announcement VARCHAR(100) NOT NULL ,date DATE NOT NULL );

CREATE TABLE interview_reservation(teacher_name VARCHAR(20) NOT NULL ,student_name VARCHAR(20) NOT NULL ,date DATE NOT NULL ,start_time VARCHAR(5) NOT NULL ,end_time VARCHAR(5) NOT NULL );

CREATE TABLE classes(id SERIAL, class_name NOT NULL, primary key(id) );

3.以下のSQLで、ログインIDとパスワード、名前を設定して実行してください。 INSERT INTO administrator (login_id, password, name) VALUES ('ログインID','パスワード', '名前' );
