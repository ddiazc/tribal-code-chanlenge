create table if not exists credit_line_authorization (
tax_id varchar(50) primary key,
founding_type varchar(50),
cash_balance numeric,
monthly_revenue numeric,
requested_credit_line numeric,
requested_date timestamptz,
status varchar(20)
)