-- to get hourly and daily data
select * from log  
where request_date >= str_to_date('2017-01-01 15:00:00','%Y-%m-%d %T')  
and request_date <=  str_to_date('2017-01-01 16:00:00','%Y-%m-%d %T') 
having count(request_ip)>200; 

-- to find requests by IP				
select * from log where request_ip = '192.168.46.138';