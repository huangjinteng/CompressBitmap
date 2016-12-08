# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

add_library( native-lib
             SHARED
             src/main/cpp/native-lib.cpp )


find_library( log-lib
              log )

target_link_libraries( native-lib jpegbither jnigraphics
                       ${log-lib} )

add_library( jpegbither
             SHARED
             IMPORTED)
set_target_properties( jpegbither
                       PROPERTIES IMPORTED_LOCATION
                       ../../../../libs/armeabi-v7a/libjpegbither.so )

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

include_directories(libs/libjpeg-turbo)