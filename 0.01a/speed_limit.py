from typing import Any, Optional

def safe_input(data_type: type, message: Optional[str]):
    message = message or ""

    while True:
        try:
            input_str = input(message)
            return data_type(input_str)
        except:
            print("Invalid input, please try again.")

def main():
    speed_limit_mph: int = safe_input(int, "Enter the speed limit (integer, mph): ")
    actual_speed_mph: int = safe_input(int, "What was the actual speed (integer, mph): ")

    speed_difference = actual_speed_mph - speed_limit_mph

    if(speed_difference <= 0):
        print("Was not over the speed limit. There is no fine.")
    elif(speed_difference < 16):
        print("The fine is $75")
    elif(speed_difference < 25):
        print("The fine is $125")
    else:
        print("The fine is $215")

if __name__ == "__main__":
    main()