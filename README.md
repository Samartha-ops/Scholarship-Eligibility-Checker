 Scholarship-Eligibility-Checker
 
 Functional Modules
1. Student Data Input & Validation  
2. Scholarship Eligibility Checking  
3. Scholarship Amount Calculation  
4. File Output Generation  
5. Database Storage (Oracle JDBC)

  Test Cases and Results

 Test Case 1: Valid Student Eligibility
- Input: Age = 19, GPA = 8.5, Income = 250000, Field = Engineering  
- Expected Result: Scholarship awarded  
- Actual Result: Scholarship awarded successfully  
- Status: Pass 

 Test Case 2: Age Below Eligibility
- Input: Age = 16  
- Expected Result: Not eligible message  
- Actual Result: Application terminated with warning  
- Status: Pass 

 Test Case 3: Invalid GPA
- Input: GPA = 12  
- Expected Result: Exception raised  
- Actual Result: “GPA must be 0–10” error shown  
- Status: Pass 

 Test Case 4: Income Above Limit
- Input: Income = 600000  
- Expected Result: Invalid input error  
- Actual Result: Exception message displayed  
- Status: Pass

 Test Case 5: Gender Restriction Check
- Input: Male applying for Women in Engineering Scholarship  
- Expected Result: Scholarship skipped  
- Actual Result: Scholarship correctly skipped  
- Status: Pass 

 Test Case 6: No Eligible Scholarship
- Input: GPA = 5.0  
- Expected Result: No scholarship available  
- Actual Result: Correct message shown  
- Status: Pass 

 Test Case 7: Scholarship Amount Calculation
- Input: GPA = 9.2, Reservation = SC, Gender = Female  
- Expected Result: Higher amount range  
- Actual Result: Amount calculated correctly  
- Status: Pass 

 Test Case 8: Special Category Benefit
- Input: Athlete  
- Expected Result: Additional amount added  
- Actual Result: Bonus applied correctly  
- Status: Pass 

 Test Case 9: File Handling
- Input: Valid student details  
- Expected Result: Text file generated  
- Actual Result: Scholarship_Result.txt created  
- Status: Pass 

 Test Case 10: Database Storage
- Input: Valid scholarship record  
- Expected Result: Data inserted into DB  
- Actual Result: Record saved successfully  
- Status: Pass 

  Results:
- Accurate eligibility determination
- Correct scholarship allocation
- Successful file and database storage
- Robust input validation and error handling

 Conclusion
The Scholarship Eligibility System successfully demonstrates the application of Java programming concepts in a real-world use case. The system is reliable, scalable, and efficient, making it suitable for academic and practical implementation.
