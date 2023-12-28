### Proje Assigment Bilgileri README-ASSIGMENT İçerisinde Bulabilirsiniz.

# DATABASE Bilgileri
PostgreSQL DB kullanılmıştır. DB Create Scripti Aşağıdaki gibidir. 

    -- Database: banksystem

    -- DROP DATABASE IF EXISTS banksystem;
    
    CREATE DATABASE banksystem
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Turkish_T�rkiye.1254'
    LC_CTYPE = 'Turkish_T�rkiye.1254'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

Database oluşturulduktan sonra application.properties dosyası içerisinde gerekli olan alanların (username, password gb.) doldurulması gerekmektedir.

Database ayarları yapıldıktan sonra Uygulama çalışınca otomatik olarak Entity Tabloları da oluşacaktır. Case dahilinde Account create olmadığı için Account manuel ekleme gerekecektir. 
Manuel Account ekleme Scripti aşağıdaki gibidir. 

İnsert Account Scripti.

    INSERT INTO public.account(
    account_number, balance, create_date, owner)
    VALUES ('cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17',	1000, now(),'Murat Demirkol');
    
# POSTMAN Bilgileri
# GET Account  

    curl --location 'localhost:8080/account/cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17'

    Response
    
    {
    "accountNumber": "cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17",
    "owner": "Murat Demirkol",
    "balance": 60.0,
    "createDate": "2023-12-28T00:35:07.073383"
    }

# POST Credit
    curl --location 'localhost:8080/account/credit/cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17' \
    --header 'Content-Type: application/json' \
    --data '{
    "amount":"15"
    }'

    Response

    {
    "status": "200 OK",
    "approvalCode": "53523ffb-1db2-4722-be18-7f16db550218"
    }
    
# POST Debit
    curl --location 'localhost:8080/account/debit/cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17' \
    --header 'Content-Type: application/json' \
    --data '{
    "amount":"2"
    }'
    
    Response
    
    {
    "status": "200 OK",
    "approvalCode": "607568f5-2998-4c86-be10-05cd63461b10"
    }
# POST Pay Bill
    curl --location 'localhost:8080/account/billPayment/vadafone/5449141361/cdc2a404-a4ff-11ee-bbfb-0f9b5d259d17' \
    --header 'Content-Type: application/json' \
    --data '{
    "amount":"13"
    }'
    
    Response
    
    {
    "status": "200 OK",
    "approvalCode": "6d3e4018-e775-4123-8216-dd9f02ce98df"
    }
