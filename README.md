#### **Veri Seti**

-28x28 piksel boyutunda

-Tam boyutu 784 piksel

-70.000 adet 

-Siyah zemin beyaz yazı 

![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/mnist_sample.png)
 
#### **Kullanılan Teknolojiler**

-CNN mimarileri için Keras kütüphanesi kullanılmıştır.

-Yapay sinir ağı modelleri Python dili kullanılarak geliştirilmiştir.

-Eğitilen sinir ağları h5f uzantısı ile kayıt edilmiştir.

-DeepLearning4j kütüphanesi ile java tarafında kullanıma hazır hale getirilmiştir.

-Yeni veri seti hazırlama için JAVA Swing teknolojisi kullanılmıştır.

### **CNN Mimarileri**

**Deep Learning Türkiye github kaynağı**

	32 x 3 x 3 CONV (aktivasyon olarak relu fonksiyonu eklenmiş.)
	64 x 3 x 4 CONV (aktivasyon olarak relu fonksiyonu eklenmiş.)
	2 x 2 MAX POOL
	DROPOUT (%25)
	128 FC (aktivasyon olarak relu fonksiyonu eklenmiş.)
	DROPOUT (%50)
	10 FC

![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/dl_turkiye.png)
 

**Yassine Ghouzam’a ait çalışma kaynak: kaggle**

    32 x 5 x 5 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    32 x 5 x 5 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    2 x 2 MAX POOL
    DROPOUT (%25)
    64 x 3 x 3 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    64 x 3 x 3 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    2 x 2 MAX POOL
    DROPOUT (%25)
    256 FC (aktivasyon olarak relu fonksiyonu eklenmiş.)
    DROPOUT (%50)
    10 FC`
     
 ![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/Yassine_Ghouzam.png)

 **Heuristic olarak deneme yanıl ile oluşturulmuş kendi mimari modelim**
 
    128 x 5 x 5 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    32 x 5 x 5 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    2 x 2 MAX POOL
    DROPOUT (%25)
    32 x 3 x 3 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    32 x 3 x 3 CONV (aktivasyon olarak relu fonksiyonu eklenmiş)
    2 x 2 MAX POOL
    256 FC (aktivasyon olarak relu fonksiyonu eklenmiş.)
    DROPOUT (%50)
    10 FC

 ![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/hcg.png)
  
 
#### **Yeni Veri Oluşturma**

    1- Kullanıcı çizim panelinde veri girişi yapar.
    
    2- Panel resim olarak alınır.
    
    3- Çizim yapılan veri 0 border ile kesilip alınır.
    
    4- 20x20 piksel boyutuna çevrilir.
    
    5- Zemin siyah yazı beyaz çevrilir.
    
    6- 28x28 piksel olacak şekilde border eklenir.
    
    7- Eğitilmiş yapay sinir ağına sunulup çıktı oluşturulur.
    
### **Uygulama Arayüzü ve Örnek Çıktıları**
    
![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/app-1.png)
  
![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/app-2.png)
    
![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/app-3.png)
   
![alt text](https://github.com/hcguler/handwrittendigitrecognition/blob/master/app/src/com/hcg/digitrecognition/images/app-4.png)
    
     
 