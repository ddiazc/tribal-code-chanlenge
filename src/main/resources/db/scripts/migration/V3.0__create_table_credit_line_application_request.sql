create table if not exists credit_line_request (
id serial primary key,
tax_id varchar(50),
created_at timestamptz default now()
)