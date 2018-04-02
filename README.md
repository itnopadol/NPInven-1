# สรุป Requirements 

* system : NpInven
* Module : Reorder,นับสต๊อกสินค้า,โอนสินค้า,เช็คสต๊อกสินค้า,จัดการที่เก็บสินค้า
เพิ่มที่เก็บสินค้า,จัดโปรโมชั่น และขอพิมป้าย
* Objective : เพื่อสามารถจัดการข้อมูลสต๊อกสินค้า,ที่เก็บสินค้า,โปรโมชั่น,ข้อพิมป้าย,โอนสินค้าและจัดการที่เก็บสินค้า 
* Requirements : ข้อมูลและรายละเอียดสต๊อกสินค้า,ที่จัดเก็บสินค้า้,โปรโมชั่นและขอพิมป้าย

# Process Description 
 * Input data flows : 
 * Output data flows : NpInven ช่วยสนับสนุนการทำงาน
# Description Process
 * 1. ระบบเก่ายังเป็นเอกสารและไม่มีฐานข้อมูลในดาต้าเบส ซึ่งยากต่อการเข้าถึงข้อมูล,เรียกใช้ และ เพิ่มข้อมูล โดยแอพพลิเคชั่นช่วยจัดการเก็บข้อมูลให้เป็นระบบ 
 * 2. ซึ่งในแอพพลิเคชั่น จะมีระบบการนับสต๊อกสินค้า ระบบโอนสินค้า ระบบเช็คสต๊อก ระบบจัดการที่เก็บสินค้า ระบบโปรโมชั่น และระบบพิมพ์ป้าย 
 * 3. ระบบนับสต๊อก จะช่วยในการนับสต๊อกได้อย่างรวดเร็ว และ จัดเก็บข้อมูลไว้ในฐานข้อมูล เพื่อสามารถเรียกดูข้อมูลได้สะดวก 
 * 4. ระบบโอนสินค้า จะช่วยการโอนสินค้าได้รวดเร็วและสะดวก
 * 5. ระบบเช็คสต๊อก จะช่วยให้ผู้ใช้ตรวจสอบสินค้า และทราบข้อมูลสินค้าได้อย่างรวดเร็ว
 * 6. ระบบจัดการที่เก็บสินค้า จะช่วยให้ผู้ใช้สามารถทราบว่าสินค้าที่จัดเก็บอยู่ไหน
 * 7. ระบบโปรโมชั่น ผู้ใช้สามารถดูรายละเอียดของโปรโมชั่นได้ และเพิ่มโปรโมชั่นได้
 * 8. ขอพิมพ์ป้าย ผู้ใช้สามารถพิมพ์ป้าย ตามจำนวนต้องการของผู้ใช้

# Menu โปรโมชั่น
**API ค้นหาโปรโมชั่น**

**API ลบโปรโมชั่น**

**API รายละเอียดโปรโมชั่น**
* DocNo:
* วันที่ขอโปรโมชั่น:
* เลขที่โปรโมชั่น:

**API แสดงรายละเอียดสินค้า**
* ลำดับ:
* ชื่อสินค้า:	
* ราคาปกติ:
* ราคาโปร:

**API เลือกโปรโมชั่น**
* เลือกรายการโปรโมชั่น:
* เลือกประเภทโปรโมชั่น:
* เลือก Section:

**API ค้นหาสินค้า**
* รหัสสินค้า:
* ชื่อสินค้า:

**API จัดโปรโมชั่นสินค้า**
* รหัสสินค้า:
* ชื่อสินค้า:
* ราคาปกติ:
* หน่วยนับ:
* สมาชิก (ลด 3%):
* IsBrochure:
* ค่าคอมเงินสด:
* ค่าคอมสินเชื่อ:
* ชื่อแคมเปญ:
* เริ่มโปรโมชั่น:
* จบโปรโมชั่น:

# Menu ขอพิมพ์ป้าย
 **API ขอพิมพ์ป้าย**
* เลือก ธรรมดา หรือ พิเศษ:
* ขนาด(P1 21ดวง/หน้า,P2 3ดวง/หน้า,P3 2ดวง/หน้า,P4 A4) :
* รหัสสินค้า:
* ชื่อสินค้า:
* จำนวนที่ต้องการพิม:

 **API ค้นหายี่ห้อ:**
* ค้นหายี่ห้อ::
* รหัสสินค้า:
* แสดงชื่อสินค้า:

 **API ค้นหายี่ห้อ:**
* รหัสสินค้า::
* ชื่อสินค้า:
* ประเภทกระดาษ:
* จำนวน:
* หน่วยนับ:
