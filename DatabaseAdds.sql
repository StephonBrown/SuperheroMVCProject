use superherosighting;

insert into Hero(HeroName, HeroDescription)
values("Earth Pounder", "A man that controls earth");

insert into Hero(HeroName, HeroDescription)
values("Water Bender", "A woman controls water");

Insert into Location(LocationName, LocationDescription,Longitude, Latitude, Street, State, ZipCode, City)
values("Egypt","In front of the Sphinx", 50.712772, 34.006058, "500 Alham St.", "Arab States","11311", "Cairo");

Insert into Location(LocationName, LocationDescription,Longitude, Latitude, Street, State, ZipCode, City)
values("Big Ben","At the foot of Lincoln", 20.889931, 30.009003, "567 Walooloo", "Westminster","90856", "London");

Insert into Organization(OrganizationName, OrganizationDescription, LocationID, TelephoneNumber)
values("Ninja","The headquarters of the clan", 3,"333-333-5355");

Insert into Organization(OrganizationName, OrganizationDescription, LocationID, TelephoneNumber)
values("Samurai Heroes","The headquarters of The Samurai Heroes", 4,"222-222-2222");

insert into Superpower(SuperPowerName,SuperPowerDescription)
values("Earth Power", "Control the elements of Earth");

insert into Superpower(SuperPowerName,SuperPowerDescription)
values("Water Power", "Control the elements of water");

Insert into Sighting(SightingDate, LocationID)
values("2013-03-10",3);

Insert into Sighting(SightingDate, LocationID)
values("2012-02-02",4);

insert into hero_superpower(HeroID, SuperpowerID)values(3,3);
insert into hero_superpower(HeroID, SuperpowerID)values(4,4);

insert into hero_sighting(HeroID, SightingID)values(3,3);
insert into hero_sighting(HeroID, SightingID)values(4,4);

insert into organization_hero(OrganizationID, HeroID)values(3,3);
insert into organization_hero(OrganizationID, HeroID)values(4,4);


insert into Member(FirstName, LastName, OrganizationID)values("Bill", "Abdul", 3);
insert into Member(FirstName, LastName, OrganizationID)values("Jane", "Walakum", 3);
insert into Member(FirstName, LastName, OrganizationID)values("Griff", "Akim", 3);

insert into Member(FirstName, LastName, OrganizationID)values("May", "Aris", 4);
insert into Member(FirstName, LastName, OrganizationID)values("Jem", "Booblay", 4);
insert into Member(FirstName, LastName, OrganizationID)values("Galler", "Yessri", 4);