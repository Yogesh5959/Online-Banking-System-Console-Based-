
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

class Student
{
    private int id;
    private String name;
    private double marks;
    Student(int id,String name,double marks)
    {
        this.id=id;
        this.name=name;
        this.marks=marks;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public double getMarks()
    {
        return marks;
    }
    public void setMarks(double marks)
    {
        this.marks=marks;
    }


    public String toString()
    {
        return id+" "+name+" "+marks;
    }
    public boolean equals(Object o)
    {
        if(this ==o)
        {
            return true;
        }
        if(!(o instanceof Student))return false;
        Student st=(Student)o;
        return st.id==id;
    }
    @Override
    public int hashCode()
    {
        return Integer.hashCode(id);
    }
}
class StudentNotFoundException extends Exception
{
    StudentNotFoundException()
    {
        
        super("STUDENT NOT FOUND");
    }
}
class StudentService
{

    List<Student> student=new ArrayList<>();
    public void addStudent(Student st)
    {
        student.add(st);
    }
    public void removeStudent(int id)throws Exception
    {

        if(student.isEmpty())
        {
            throw new StudentNotFoundException();
        
        }
        else
        {
            ListIterator<Student> lt=student.listIterator();
            while(lt.hasNext())
            {
                Student st=(Student)lt.next();
                if(st.getId()==id)
                
                {
                    lt.remove();
                    return;
                }
            }
            throw new StudentNotFoundException();
        }
        
    }
    
    public Student searchStudent(int id)throws Exception
    {
        if(student.isEmpty())
        {
            throw new StudentNotFoundException();
        }
        else
        {
            ListIterator<Student> lt=student.listIterator();
            while(lt.hasNext())
            {
                Student st=(Student)lt.next();
                if(st.getId()==id)
                {
                    return st;
                }
            }
            throw new StudentNotFoundException();
        }
    }

    
    public void updateStudent(int id,Student st)throws Exception
    {
        ListIterator<Student> lt=student.listIterator();
        
        
            if(student.isEmpty())throw new StudentNotFoundException();
            while(lt.hasNext())
            {
                Student s2=(Student)lt.next();
                if(s2.getId()==id)
                {
                    lt.set(st);
                    
                    return;
                }
            }
            throw new StudentNotFoundException();
        
    }
    public void displayAllStudents()
    {
        if(student.isEmpty())
        {
            System.out.println("EMPTY LIST");
        }
        else
        {
            System.out.println("STUDENT DETAILS: ");
            ListIterator<Student> lt=student.listIterator();

            while(lt.hasNext())
            {
                System.out.println(lt.next());
            }
        }
    }
    public void saveFile()throws IOException
    {
        PrintWriter pw=new PrintWriter("student.txt");
        ListIterator<Student> lt=student.listIterator();

        if(student.isEmpty())
        {
            System.out.println("LIST IS EMPTY");
            pw.close();
            return;
        }
        while(lt.hasNext())
        {
            pw.println(lt.next());
        }
        pw.flush();
        pw.close();
    }
}
public class Main
{
    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        StudentService studentService=new StudentService();
        System.out.println("               **** STUDENT MANAGEMENT PORTAL ****");
        System.out.println();
        while(true)
        {
            System.out.println("ENTER YOUR CHOICE:");
            System.out.println("1.ADD STUDENT \n2.VIEW STUDENTS \n3.SEARCH STUDENT \n4.UPDATE STUDENT \n5.DELETE STUDENT \n6.SAVE TO FILE \n7.EXIT \n");
            
            switch(sc.nextInt())
            {
                case 1:
                    System.out.println("ENTER STUDENT DETAILS: ");
                    System.out.print("STUDENT ID:");
                    int id=sc.nextInt();
                    System.out.print("STUDENT NAME: ");
                    String name=sc.next();
                    System.out.print("STUDENT MARKS: ");
                    double marks=sc.nextDouble();
                    
                    studentService.addStudent(new Student(id, name, marks));
                    System.out.println("STUDENT DETAILS ADDED SUCCESSFULLY\n");
                    System.out.println();
                    break;
                case 2:

                    studentService.displayAllStudents();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("ENTER THE STUDENT ID TO SEARCH");
                    System.out.print("STUDENT ID: ");
                    int searchid=sc.nextInt();
                    try
                    {
                        System.out.println(studentService.searchStudent(searchid));
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;

                case 4:
                   System.out.println("ENTER THE STUDENT ID:");
                   System.out.println("STUDENT ID:");
                   int updateid=sc.nextInt();
                   try
                   {
                        System.out.println(studentService.searchStudent(updateid));
                        System.out.println("ENTER THE UPDATE DETAILS");
                        
                        System.out.print("\nSTUDENT NAME: ");
                        String upname=sc.next();
                        System.out.print("\nSTUDENT MARKS: ");
                        double upmarks=sc.nextDouble();
                        Student newst=new Student(updateid, upname, upmarks);
                        studentService.updateStudent(updateid, newst);
                        System.out.println("STUDENT DETAILS UPDATED SUCCESSFULLY");

                   }
                   catch(Exception e)
                   {
                        System.out.println(e.getMessage());
                   }
                   System.out.println();
                   break;
                case 5:
                   System.out.println("ENTER THE STUDENT ID TO REMOVE");
                   System.out.print("STUDENT ID: ");
                   int removeid=sc.nextInt();
                   try
                   {
                        studentService.removeStudent(removeid);
                        System.out.println("STUDENT DETAILS REMOVED SUCCESSFULLY");
                   }
                   catch(Exception e)
                   {
                        System.out.print(e.getMessage());
                   }
                   System.out.println();
                   break;
                case 6:
                   try
                   {
                        studentService.saveFile();
                        System.out.println("DATA STORED IN Student.txt");

                   }
                   catch(Exception e)
                   {
                        System.out.println(e.getMessage());
                   }
                   System.out.println();
                   break;

                case 7:
                   System.out.println("EXIT");
                   sc.close();
                   System.exit(0);
                default:
                   System.out.println("INVALID CHOICE , ENTER VALID");
                   System.out.println();
                   
                   break;
                
            }
            
        }
        

    }
}