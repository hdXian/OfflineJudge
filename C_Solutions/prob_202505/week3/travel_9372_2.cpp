#include <iostream>

using namespace std;

int calc(int n, int m) {

    string buf;
    getline(cin, buf, '\n');
    for(int i=0; i<m; i++) getline(cin, buf, '\n');

    return n-1;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    // 기본 설정: ios_base::sync_with_stdio(true);
    // 비활성화: ios_base::sync_with_stdio(false);
    // c의 printf, scanf 버퍼와 c++의 cin, cout 버퍼를 묶어서 사용.
    // 즉 코드 내에서 cin, cout과 print, scanf를 섞어서 써도 데이터가 꼬이지 않는다. (원래 둘이 서로 다른 버퍼를 쓰는듯)

    // 기본 설정: cin.tie(&cout);
    // 비활성화: cin.tie(NULL)
    // cin으로 입력받기를 시작하기 전에 반드시 cin의 버퍼를 비우는 설정. (cin과 cout을 묶음 설정한다)
    // cout은 내부 버퍼에 데이터를 채워놨다가 꽉 차거나 flush 명령이 떨어져야만 출력한다.
    // cin.tie(&cout) 설정 후 cin >>, endl, cout.flush(), 버퍼 가득참 등의 경우에 버퍼가 flush된다.

    int T;
    cin >> T;

    int N, M;
    string result = "";
    for(int i=0; i<T; i++) {
        cin >> N >> M;
        result += to_string(calc(N, M)) + '\n';
    }

    cout << result << endl;

    return 0;
}
