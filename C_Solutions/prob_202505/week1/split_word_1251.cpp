#include <iostream>
#include <algorithm>

using namespace std;

string word = "";
string result = "";

void comb(string prefix, string postfix, int depth) {
    // cout << "comb(" << prefix << ", " << postfix << ", " << depth << ")" << endl;
    if (depth == 3) {
        // cout << "depth 3" << endl;
        // cout << "prefix: " << prefix << endl;
        // cout << "postfix: " << postfix << endl;
        
        postfix = string(postfix.rbegin(), postfix.rend());
        string res = prefix + postfix;
        // cout << "res: " << res << endl;

        result = min(result, res);
        // cout << "min result: " << result << endl;
    }
    else {
        int length = postfix.length();
        // 반복 횟수는 depth가 1일 때 length-2
        // depth가 2일 때 length-1 만큼 돌려야 함.
        // length-3+depth
        for(int i=0; i<length-3+depth; i++) {
            // cout << "i: " << i << endl;

            string cur = postfix.substr(0, i+1); // postfix에서 떼올 만큼만 자르기
            cur = string(cur.rbegin(), cur.rend()); // 뒤집기
            // cout << "prefix + cur: " << (prefix + cur) << endl;

            // prefix + cur을 붙인 문자열을 prefix로, postfix의 남은 부분을 postfix로 지정해 다음 comb로 보내기
            comb(prefix + cur, postfix.substr(i+1), depth+1); 
        }
    }

}

int main() {

    cin >> word;
    result = string(word.length(), 'z'); // 같은 단어는 나올 수 없음.
    // cout << "result: " << result << endl;
    
    comb("", word, 1);

    cout << result << endl;

    return 0;
}
