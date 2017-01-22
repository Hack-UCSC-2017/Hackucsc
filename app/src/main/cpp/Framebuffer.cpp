//
// Created by Cole Faust on 1/21/17.
//

#include <jni.h>
#include <string>
#include <fcntl.h>
#include <linux/fb.h>
#include <sys/mman.h>

extern "C" {
jstring
Java_com_SteadyView_SteadyView_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

int
Java_com_SteadyView_SteadyView_MainActivity_framebuffer(JNIEnv *env, jobject ) {
    int fd;
    struct fb_var_screeninfo vi;
    struct fb_fix_screeninfo fi;
    void*                    bits;
    int                      bpp;    /* byte per pixel */
    int                      stride; /* size of stride in pixel */

    /* Open framebuffer */
    if(0 > (fd = open("/dev/graphics/fb0", O_RDWR))) {
        printf("Fail to open fb\n");
        return -1;
    }

    /* Get fixed information */
    if(0 > ioctl(fd, FBIOGET_FSCREENINFO, &fi)) {
        printf("Fail to get fixed info\n");
        return -2;
    }

    /* Get variable information */
    if(0 > ioctl(fd, FBIOGET_VSCREENINFO, &vi)) {
        printf("Failed to get variable info\n");
        return -3;
    }

    /* Get raw bits buffer */
    void* data = malloc(fi.smem_len);
    if(MAP_FAILED == (bits = mmap(data, fi.smem_len,
                                  PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0))) {
        printf("Failed to mmap fb\n");
        return -4;
    }

    /* Calculate useful information */
    bpp = vi.bits_per_pixel >> 3;
    stride = fi.line_length / bpp;


    /* Getting raw-image snapshot of current framebuffer */
    void* curbits; /* current framebuffer raw data */
    curbits = (unsigned char*)bits + (vi.xoffset + vi.yoffset*vi.xres_virtual)*bpp;
    memcpy(data, curbits, vi.yres*stride*bpp);


    /* Modifying directly */
    //do_something(curbits...); /* change buffer directly... */

    /* Refresh buffer manually */
    vi.activate |= FB_ACTIVATE_NOW | FB_ACTIVATE_FORCE;
    if(0 > ioctl(fd, FBIOPUT_VSCREENINFO, &vi)) {
        printf("Failed to refresh\n");
        return -5;
    }

    return bpp;
}
}