🖥️ This project is a simple Digital Voting System built using Core Java and MySQL. It allows registered voters to log in using their Voter ID and password, cast their vote for a candidate, and ensures one person votes only once. It also maintains a vote count and displays the result at the end. This is a console-based project, ideal for beginners to understand Java-MySQL integration with authentication logic.

🔐 Features
Voter authentication using Voter ID and password

Prevents duplicate voting

Secure vote recording into the database

Simple and clean console-based interface (can be extended to GUI or web later)

Result calculation from the database

🧱 Technologies Used
💻 Java 

🛢️ MySQL 

🗂️ Database Structure
Database: votingdb
1. voters Table
Column	Type	Description
voter_id	VARCHAR(20)	Unique ID for each voter
password	VARCHAR(50)	Password for login
voted	BOOLEAN/INT	0 = not voted, 1 = voted

2. votes Table
Column	Type	Description
name	VARCHAR(100)	Candidate name
✅ How It Works
User logs in using Voter ID and Password.

System checks if the voter has already voted.

If not, voter selects a candidate.

Vote count is updated, and voter status is set to “voted”.

Results can be viewed by admin after voting ends.

count	INT	Total number of votes received

✅ A simple and secure Java + MySQL based voting system to demonstrate core authentication and database handling.

