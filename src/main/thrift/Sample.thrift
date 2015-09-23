namespace java 	sir.wellington.commons.thrift.generated

typedef i32 int
typedef i64 long

struct Iphone
{
    1: string name;
}

struct Android
{
    1: string name;
}

union Phone
{
    1: Iphone iphone;
    2: Android android;
}

struct SampleRequest
{
    1: string argument;
    2: Phone phone;
}

struct SampleResponse
{
    1: string message;
}

service SampleService
{
    SampleResponse request(1: SampleRequest request)
}