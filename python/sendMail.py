import smtplib
from email.mime.text import MIMEText
 
def send_email(subject, body, to_email):
    from_email = "your_email@gmail.com"
    password = "your_password"
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = from_email
    msg['To'] = to_email
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(from_email, password)
    server.sendmail(from_email, to_email, msg.as_string())
    server.quit()
 
subject = "Test Email"
body = "This is a test email sent from Python."
to_email = "recipient_email@gmail.com"
send_email(subject, body, to_email)