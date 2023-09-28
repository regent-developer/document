from sqlalchemy import Column, String, create_engine, Integer, DateTime
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from datetime import datetime

engine = create_engine("sqlite:///db.sqlite3")
Session = sessionmaker()
Session.configure(bind=engine)
session = Session()

Base= declarative_base(engine)

class Test(Base):
    __tablename__ = 'table_name'
    id = Column(Integer, autoincrement=True, primary_key=True)
    name = Column(String(50))



del_list = [Test.__table__,]


# Base.metadata.drop_all(tables=del_list)
Base.metadata.create_all(tables=del_list)

item_dict = {
    'name': 'test'
}
item = Test(**item_dict)
session.add(item)
session.commit()
