def print_land(land):
    for i in land:
        print("".join(i))

k, x, n = map(int, input().split(" "))
snake_coords = [0, 0]
if n == 1:
    print("#"*(x+1))
else:
    snake_pos = n % 4 

    if snake_pos == 3:

        side_length = x * k**(n-1) + 1
        top_length = x * k**(n-2) + 1
        land = []
        for i in range(top_length):
            land.append(["."] * side_length)

        land[0] = ["#"] * side_length

        for i in range(top_length):
            land[top_length-1-i][side_length-1] = "#"

        snake_coords = [top_length-1, side_length-1]
        
    elif snake_pos == 0:

        side_length = x * k**(n-2) + 1
        top_length = x * k**(n-1) + 1
        land = []
        for i in range(top_length):
            land.append(["."] * side_length)

        for i in range(top_length):
            land[i][0] = "#"
        
        land[0] = ["#"] * side_length

        snake_coords = [0, side_length-1]

    elif snake_pos == 1:

        side_length = x * k**(n-1) + 1
        top_length = x * k**(n-2) + 1
        land = []
        for i in range(top_length):
            land.append(["."] * side_length)

        land[top_length-1] = ["#"] * side_length

        for i in range(top_length):
            land[i][0] = "#"
        
        snake_coords = [0, 0]

    elif snake_pos == 2:

        side_length = x * k**(n-2) + 1
        top_length = x * k**(n-1) + 1
        land = []
        for i in range(top_length):
            land.append(["."] * side_length)

        for i in range(top_length):
            land[top_length-1-i][side_length-1] = "#"
            
        land[top_length-1] = ["#"] * side_length

        snake_coords = [top_length-1, 0]

    n -= 2

    while n != 0:
        if n % 4 == 1:
            for i in range(x * k**(n-1) + 1):
                land[snake_coords[0]][snake_coords[1] - i] = "#"
            snake_coords = [snake_coords[0], snake_coords[1] - x * k**(n-1)]
        elif n % 4 == 2:
            for i in range(x * k**(n-1) + 1):
                land[snake_coords[0] + i][snake_coords[1]] = "#"
            snake_coords = [snake_coords[0] + x * k**(n-1), snake_coords[1]]
        elif n % 4 == 3:
            for i in range(x * k**(n-1) + 1):
                land[snake_coords[0]][snake_coords[1] + i] = "#"
            snake_coords = [snake_coords[0], snake_coords[1] + x * k**(n-1)]
        elif n % 4 == 0:
            for i in range(x * k**(n-1) + 1):
                land[snake_coords[0] - i][snake_coords[1]] = "#"
            snake_coords = [snake_coords[0] - x * k**(n-1), snake_coords[1]]
        n -= 1
    print_land(land)