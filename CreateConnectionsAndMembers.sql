use superherosighting;

delete from hero_superpower where HeroID = 1;
delete from hero_superpower where HeroID = 2;
delete from hero_sighting where HeroID = 1;
delete from hero_sighting where HeroID = 2;

insert into hero_superpower(HeroID, SuperpowerID)values(1,1);
insert into hero_superpower(HeroID, SuperpowerID)values(2,2);

insert into hero_sighting(HeroID, SightingID)values(1,1);
insert into hero_sighting(HeroID, SightingID)values(2,2);

insert into organization_hero(OrganizationID, HeroID)values(1,1);
insert into organization_hero(OrganizationID, HeroID)values(2,1);

insert into organization_hero(OrganizationID, HeroID)values(1,2);
insert into organization_hero(OrganizationID, HeroID)values(2,2);

insert into Member(FirstName, LastName, OrganizationID)values("Bill", "Sneezer", 1);
insert into Member(FirstName, LastName, OrganizationID)values("Jane", "Willps", 1);
insert into Member(FirstName, LastName, OrganizationID)values("Griff", "Dooby", 1);

insert into Member(FirstName, LastName, OrganizationID)values("May", "Frim", 2);
insert into Member(FirstName, LastName, OrganizationID)values("Jem", "Eli", 2);
insert into Member(FirstName, LastName, OrganizationID)values("Galler", "Goobs", 2);