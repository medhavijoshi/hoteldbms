drop trigger if exists checkdates_on_insertion;
DELIMITER %%;
create trigger checkdates_on_insertion
before insert on reservations
for each row
begin
	if (datediff(new.endDate, new.startDate) > 30)
	then signal sqlstate '10002' set message_text = 'You cannot boook a room for more than a month';	
    else
	set new.numOfDays = datediff(new.endDate, new.startDate);
    end if;
    
    if (select distinct room.roomId 
			from room left outer join reservations on room.roomId = reservations.roomId 
			where (new.startdate = reservations.startdate 
            or (reservations.startdate < new.enddate and reservations.enddate > new.startdate)
            or (new.startdate < reservations.startdate and new.enddate > reservations.startdate)
            or new.enddate = reservations.enddate
            or new.startdate = reservations.enddate
             or (new.startdate < reservations.enddate and new.enddate > reservations.enddate)) and new.roomid = reservations.roomid)
            or new.enddate = reservations.startdate
            
	then signal sqlstate '10002' set message_text = 'Room is taken by someone else';
    end if;
	set new.totalPrice = (select pricePerNight from room where roomId = new.roomId) * new.numOfDays;
end;

drop trigger if exists checkDates_updation;
DELIMITER %%;
create trigger checkDates_updation
before update on reservations
for each row
begin
    if (datediff(new.endDate, new.startDate) > 30)
    then signal sqlstate'10002' set message_text = 'You cannot book a room for more than a month';  
    else
    set new.numOfDays = datediff(new.endDate, new.startDate);
    end if;
end;

drop trigger if exists updateResPrice;
DELIMITER %%;
create trigger updateResPrice
after insert on cleaningservice
for each row
begin
    update reservations
    set reservations.totalPrice = reservations.totalPrice + price
    where reservations.resId = resId;
end;

drop trigger if exists check_account_creation;
DELIMITER %%;
CREATE TRIGGER check_account_creation
BEFORE INSERT ON USER
FOR EACH ROW
BEGIN 
    
    IF CHAR_LENGTH(NEW.FIRSTNAME) < 1
    THEN SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'First name cannot be empty';
    END IF;
    
    IF CHAR_LENGTH(NEW.PASSWORD) < 8
    THEN SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'Password must be at least 8 characters';
    END IF;
    
    IF CHAR_LENGTH(NEW.USERNAME) < 6
    THEN SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'Username must be at least 6 characters';
    END IF;
    
    IF CHAR_LENGTH(NEW.LASTNAME) < 1
    THEN SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'Last name cannot be empty';
    END IF;
    
    IF NEW.AGE < 18
    THEN SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'Must be at least 18 years of age';
    END IF;
    
END; 