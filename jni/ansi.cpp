#include "com_github_ryoii_uploader_ANSIHelper.h"
#include <Windows.h>


JNIEXPORT void JNICALL Java_com_github_ryoii_uploader_ANSIHelper_enableANSI(JNIEnv* env, jclass clz) {
	HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);
	DWORD dwMode = 0;
	GetConsoleMode(hOut, &dwMode);
	dwMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
	SetConsoleMode(hOut, dwMode);
}

JNIEXPORT void JNICALL Java_com_github_ryoii_uploader_ANSIHelper_upLine(JNIEnv* env, jclass clz, jint n) {
	printf("\0x33[%dF", n);
}
