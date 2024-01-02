package com.ljw.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试简单excel
 *
 * @author Luo
 */
public class TestEasyExcel {
    // 测试写
    @Test
    public void testWrite(){
        String filename = "D:\\TestData.xlsx";

        //调用easyExcel里面的方法实现写操作
        //参数1：文件名称
        //参数2：对应实体类
        EasyExcel
                .write(filename,TestData.class)
                .sheet("学生列表")
                .doWrite(getLists());
    }

    @Test
    public void testReadExcel(){
        String filename="D:\\TestData.xlsx";

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel
                .read(filename, TestData.class, new ExcelListener())
                .sheet()
                .doRead();
    }


    public static List<TestData> getLists(){
        List<TestData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestData testData = new TestData();
            testData.setStuNo(i);
            testData.setStuName("ljw" + i);
            list.add(testData);
        }
        return list;
    }

    @Test
    public void testList(){
        List<Student> arrayList = new ArrayList<Student>();
        Student aa = new Student("aa", 18);
        Student bb = new Student("bb", 20);

        arrayList.add(aa);
        arrayList.add(bb);

        aa.setName("修改aa");

        for (Student s : arrayList){
            System.out.println(s);
        }
    }
}

class Student{
    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}