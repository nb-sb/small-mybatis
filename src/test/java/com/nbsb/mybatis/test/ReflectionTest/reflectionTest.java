package com.nbsb.mybatis.test.ReflectionTest;

import com.alibaba.fastjson.JSON;
import com.nbsb.mybatis.reflection.MetaObject;
import com.nbsb.mybatis.reflection.SystemMetaObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class reflectionTest {
    @Test
    public void test() {
        Teacher teacher = new Teacher();
        List<Teacher.Student> list = new ArrayList<>();
        list.add(new Teacher.Student());
        list.add(new Teacher.Student());
        teacher.setStudents(list);
        MetaObject metaObject = SystemMetaObject.forObject(teacher);

        System.out.println(JSON.toJSONString(metaObject.getGetterNames()));
        System.out.println(JSON.toJSONString(metaObject.getSetterNames()));
        if (metaObject.hasSetter("price")) {
            metaObject.setValue("price", 100.0);
        }
        if (metaObject.hasGetter("students")) {
            metaObject.setValue("students[0].id", "001");
            metaObject.setValue("students[0].name", "wang");
            metaObject.setValue("students[1].id", "002");
            metaObject.setValue("students[1].name", "ok");
        }
        System.out.println(JSON.toJSONString(metaObject.getValue("price")));

        System.out.println(JSON.toJSONString(teacher));
    }
    static class Teacher {

        private String name;

        private Double price;

        private List<Student> students;

        private Student student;

        public static class Student {

            private String id;
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }
}
