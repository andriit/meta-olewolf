MAINTAINER = "Ole Wolf <ole@naturloven.dk>"
HOMEPAGE = "http://www.boost.org/"
SUMMARY = "Boost C++ Libraries"
DESCRIPTION = "The Boost web site provides free, peer-reviewed, portable C++ source libraries. The emphasis is on libraries which work well with the C++ Standard Library. One goal is to establish "existing practice" and provide reference implementations so that the Boost libraries are suitable for eventual standardization. Some of the libraries have already been proposed for inclusion in the C++ Standards Committee's upcoming C++ Standard Library Technical Report."
PROVIDES = " \
	${BPN}-atomic \
	${BPN}-chrono \
	${BPN}-context \
	${BPN}-coroutine \
	${BPN}-date-time \
	${BPN}-exception \
	${BPN}-filesystem \
	${BPN}-graph \
	${BPN}-iostreams \
	${BPN}-locales \
	${BPN}-log \
	${BPN}-math \
	${BPN}-program-options \
	${BPN}-random \
	${BPN}-regex \
	${BPN}-serialization \
	${BPN}-signals \
	${BPN}-system \
	${BPN}-thread \
	${BPN}-timer \
	${BPN}-unit-test-framework \
	${BPN}-wave \
	${BPN}-dev \
"
PR = "r0"

SRC_URI = " \
	http://downloads.sourceforge.net/project/boost/boost/1.55.0/boost_1_55_0.tar.gz \
"
SRC_URI[md5sum] = "93780777cfbf999a600f62883bd54b17"
SRC_URI[sha256sum] = "19c4305cd6669f2216260258802a7abc73c1624758294b2cad209d45cc13a767"


LICENSE = "Boost"
LIC_FILES_CHKSUM = "file://LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c"

S = "${WORKDIR}/boost_1_55_0/"
PACKAGES_prepend = " \
	${BPN}-exception \
	${BPN}-test-framework \
	${BPN}-log \
	${BPN}-math \
	${BPN}-atomic \
	${BPN}-chrono \
	${BPN}-context \
	${BPN}-coroutine \
	${BPN}-date-time \
	${BPN}-filesystem \
	${BPN}-graph \
	${BPN}-iostreams \
	${BPN}-locales \
	${BPN}-program-options \
	${BPN}-random \
	${BPN}-regex \
	${BPN}-serialization \
	${BPN}-signals \
	${BPN}-system \
	${BPN}-thread \
	${BPN}-timer \
	${BPN}-wave \
"

FILES_${BPN}-dbg += "${libdir}/.debug/*"
FILES_${BPN}-dev += "${libdir}/*.so"
FILES_${BPN}-atomic = " ${libdir}/${BPN}_atomic.so.* "
FILES_${BPN}-chrono = " ${libdir}/${BPN}_chrono.so.* "
FILES_${BPN}-context = " ${libdir}/${BPN}_context.so.* "
FILES_${BPN}-coroutine = " ${libdir}/${BPN}_coroutine.so.* "
FILES_${BPN}-date-time = " ${libdir}/${BPN}_date_time.so.* "
FILES_${BPN}-exception = " ${libdir}/${BPN}_exception.a "
FILES_${BPN}-filesystem = " ${libdir}/${BPN}_filesystem.so.* "
FILES_${BPN}-graph = " ${libdir}/${BPN}_graph.so.* "
FILES_${BPN}-iostreams = " ${libdir}/${BPN}_iostreams.so.* "
FILES_${BPN}-locales = " ${libdir}/${BPN}_locale.so.* "
FILES_${BPN}-log = " ${libdir}/${BPN}_log*.so.* "
FILES_${BPN}-math = " ${libdir}/${BPN}_math_*.so.* "
FILES_${BPN}-program-options = " ${libdir}/${BPN}_program_options.so.* "
FILES_${BPN}-random = " ${libdir}/${BPN}_random.so.* "
FILES_${BPN}-regex = " ${libdir}/${BPN}_regex.so.* "
FILES_${BPN}-serialization = " ${libdir}/${BPN}_serialization.so.* "
FILES_${BPN}-signals = " ${libdir}/${BPN}_signals.so.* "
FILES_${BPN}-system = " ${libdir}/${BPN}_system.so.* "
FILES_${BPN}-test = " ${libdir}/${BPN}_test_exec_monitor.a ${libdir}/${BPN}_unit_test_framework.so.* ${libdir}/${BPN}_prg_exec_monitor.so.* "
FILES_${BPN}-thread = " ${libdir}/${BPN}_thread.so.* "
FILES_${BPN}-timer = " ${libdir}/${BPN}_timer.so.* "
FILES_${BPN}-wave = " ${libdir}/${BPN}_wave.so.* "


do_configure () {
	# Indicate cross-compilation.
	echo "using gcc : bitbake : ${CXX} ;" > ${S}tools/build/v2/user-config.jam
	exit 0
}

do_compile () {
	# Build the build app.
	CC="${CCACHE}${BUILD_PREFIX}gcc ${BUILD_CC_ARCH}" CXX="${CCACHE}${BUILD_PREFIX}g++ ${BUILD_CC_ARCH}" CPP="${BUILD_PREFIX}gcc ${BUILD_CC_ARCH} -E" LD="${BUILD_PREFIX}ld ${BUILD_LD_ARCH} " CPPFLAGS="${BUILD_CPPFLAGS}" CFLAGS="${BUILD_CFLAGS}" CXXFLAGS="${BUILD_CFLAGS}" LDFLAGS="${BUILD_LDFLAGS}" ./bootstrap.sh --prefix=${D}${prefix}
	# Use the builder app to build the libraries.
	./b2 --toolset=gcc-bitbake target-os=linux stage threading=multi link=shared || true
	exit 0
}

fakeroot do_install () {
	./b2 --toolset=gcc-bitbake target-os=linux install threading=multi link=shared || true
	exit 0
}


# For direct fetching of the most recent version (untested recipe):
#SRC_URI = " \
#	git://github.com/boostorg/boost.git;destsuffix=git \
#	git://github.com/boostorg/build.git;destsuffix=git/tools/build \
#	git://github.com/boostorg/thread.git;destsuffix=git/libs/system \
#	git://github.com/boostorg/math.git;destsuffix=git/libs/math \
#	git://github.com/boostorg/integer.git;destsuffix=git/libs/integer \
#	git://github.com/boostorg/chrono.git;destsuffix=git/libs/chrono \ 
#	git://github.com/boostorg/crc.git;destsuffix=git/libs/crc \
#	git://github.com/boostorg/iostreams.git;destsuffix=git/libs/iostreams \
#	git://github.com/boostorg/graph.git;destsuffix=git/libs/graph \
#	git://github.com/boostorg/uuid.git;destsuffix=git/libs/uuid \
#	git://github.com/boostorg/algorithm.git;destsuffix=git/libs/algorithm \
#	git://github.com/boostorg/array.git;destsuffix=git/libs/array \
#	git://github.com/boostorg/bind.git;destsuffix=git/libs/bind \
#	git://github.com/boostorg/config.git;destsuffix=git/libs/config \
#	git://github.com/boostorg/assign.git;destsuffix=git/libs/assign \
#	git://github.com/boostorg/atomic.git;destsuffix=git/libs/atomic \
#	git://github.com/boostorg/date_time.git;destsuffix=git/libs/date_time \
#	git://github.com/boostorg/heap.git;destsuffix=git/libs/heap \
#	git://github.com/boostorg/exception.git;destsuffix=git/libs/exception \
#	git://github.com/boostorg/lambda.git;destsuffix=git/libs/lambda \
#	git://github.com/boostorg/iterator.git;destsuffix=git/libs/iterator \
#	git://github.com/boostorg/filesystem.git;destsuffix=git/libs/filesystem \
#	git://github.com/boostorg/log.git;destsuffix=git/libs/log \
#	git://github.com/boostorg/config.git;destsuffix=git/libs/config \
#	git://github.com/boostorg/optional.git;destsuffix=git/libs/optional \
#	git://github.com/boostorg/utility.git;destsuffix=git/libs/utility \
#	git://github.com/boostorg/preprocessor.git;destsuffix=git/libs/preprocessor \
#	git://github.com/boostorg/type_erasure.git;destsuffix=git/libs/type_erasure \
#	git://github.com/boostorg/typeof.git;destsuffix=git/libs/typeof \
#	git://github.com/boostorg/program_options.git;destsuffix=git/libs/program_options \
#	git://github.com/boostorg/type_traits.git;destsuffix=git/libs/type_traits \
#	git://github.com/boostorg/serialization.git;destsuffix=git/libs/serialization \
#	git://github.com/boostorg/signals.git;destsuffix=git/libs/signals \
#	git://github.com/boostorg/signals2.git;destsuffix=git/libs/signals2 \
#	git://github.com/boostorg/smart_ptr.git;destsuffix=git/libs/smart_ptr \
#	git://github.com/boostorg/multiprecision.git;destsuffix=git/libs/multiprecision \
#	git://github.com/boostorg/parameter.git;destsuffix=git/libs/parameter \
#	git://github.com/boostorg/lockfree.git;destsuffix=git/libs/lockfree \
#	git://github.com/boostorg/logic.git;destsuffix=git/libs/logic \
#	git://github.com/boostorg/range.git;destsuffix=git/libs/range \
#	git://github.com/boostorg/circular_buffer.git;destsuffix=git/libs/circular_buffer \
#	git://github.com/boostorg/tokenizer.git;destsuffix=git/libs/tokenizer \
#	git://github.com/boostorg/variant.git;destsuffix=git/libs/variant \
#	git://github.com/boostorg/tuple.git;destsuffix=git/libs/tuple \
#	git://github.com/boostorg/timer.git;destsuffix=git/libs/timer \
#	git://github.com/boostorg/test.git;destsuffix=git/libs/test \
#	git://github.com/boostorg/tti.git;destsuffix=git/libs/tti \
#	git://github.com/boostorg/units.git;destsuffix=git/libs/units \
#	git://github.com/boostorg/random.git;destsuffix=git/libs/random \
#	git://github.com/boostorg/regex.git;destsuffix=git/libs/regex \
#	git://github.com/boostorg/statechart.git;destsuffix=git/libs/statechart \
#	git://github.com/boostorg/spirit.git;destsuffix=git/libs/spirit \
#	git://github.com/boostorg/unordered.git;destsuffix=git/libs/unordered \
#	git://github.com/boostorg/scope_exit.git;destsuffix=git/libs/scope_exit \
#	git://github.com/boostorg/detail.git;destsuffix=git/libs/detail \
#	git://github.com/boostorg/property_map.git;destsuffix=git/libs/property_map \
#	git://github.com/boostorg/static_assert.git;destsuffix=git/libs/static_assert \
#	git://github.com/boostorg/ptr_container.git;destsuffix=git/libs/ptr_container \
#	git://github.com/boostorg/property_tree.git;destsuffix=git/libs/property_tree \
#	git://github.com/boostorg/intrusive.git;destsuffix=git/libs/intrusive \
#	git://github.com/boostorg/io.git;destsuffix=git/libs/io \
#	git://github.com/boostorg/locale.git;destsuffix=git/libs/locale \
#	git://github.com/boostorg/concept_check.git;destsuffix=git/libs/concept_check \
#	git://github.com/boostorg/compose.git;destsuffix=git/libs/compose \
#	git://github.com/boostorg/bimap.git;destsuffix=git/libs/bimap \
#	git://github.com/boostorg/function.git;destsuffix=git/libs/function \
#	git://github.com/boostorg/compatibility.git;destsuffix=git/libs/compatibility \
#	git://github.com/boostorg/geometry.git;destsuffix=git/libs/geometry \
#	git://github.com/boostorg/python.git;destsuffix=git/libs/python \
#	git://github.com/boostorg/rational.git;destsuffix=git/libs/rational \
#	git://github.com/boostorg/ratio.git;destsuffix=git/libs/ratio \
#	git://github.com/boostorg/proto.git;destsuffix=git/libs/proto \
#	git://github.com/boostorg/context.git;destsuffix=git/libs/context \
#	git://github.com/boostorg/coroutine.git;destsuffix=git/libs/coroutine \
#	git://github.com/boostorg/conversion.git;destsuffix=git/libs/conversion \
#	git://github.com/boostorg/multi_index.git;destsuffix=git/libs/multi_index \
#	git://github.com/boostorg/local_function.git;destsuffix=git/libs/local_function \
#	git://github.com/boostorg/format.git;destsuffix=git/libs/format \
#	git://github.com/boostorg/dynamic_bitset.git;destsuffix=git/libs/dynamic_bitset \
#	git://github.com/boostorg/foreach.git;destsuffix=git/libs/foreach \
#	git://github.com/boostorg/fusion.git;destsuffix=git/libs/fusion \
#	git://github.com/boostorg/move.git;destsuffix=git/libs/move \
#	git://github.com/boostorg/interprocess.git;destsuffix=git/libs/interprocess \
#	git://github.com/boostorg/phoenix.git;destsuffix=git/libs/phoenix \
#	git://github.com/boostorg/disjoint_sets.git;destsuffix=git/libs/disjoint_sets \
#	git://github.com/boostorg/flyweight.git;destsuffix=git/libs/flyweight \
#	git://github.com/boostorg/predef.git;destsuffix=git/libs/predef \
#	git://github.com/boostorg/mpi.git;destsuffix=git/libs/mpi \
#	git://github.com/boostorg/mpl.git;destsuffix=git/libs/mpl \
#	git://github.com/boostorg/msm.git;destsuffix=git/libs/msm \
#	git://github.com/boostorg/gil.git;destsuffix=git/libs/gil \
#	git://github.com/boostorg/functional.git;destsuffix=git/libs/functional \
#	git://github.com/boostorg/function_types.git;destsuffix=git/libs/function_types \
#	git://github.com/boostorg/pool.git;destsuffix=git/libs/pool \
#	git://github.com/boostorg/graph_parallel.git;destsuffix=git/libs/graph_parallel \
#	git://github.com/boostorg/icl.git;destsuffix=git/libs/icl \
#	git://github.com/boostorg/polygon.git;destsuffix=git/libs/polygon \
#	git://github.com/boostorg/multi_array.git;destsuffix=git/libs/multi_array \
#	git://github.com/boostorg/wave.git;destsuffix=git/libs/wave \
#	git://github.com/boostorg/tr1.git;destsuffix=git/libs/tr1 \
#	git://github.com/boostorg/xpressive.git;destsuffix=git/libs/xpressive \
#"

#git://github.com/boostorg/thread.git;name=thread;destsuffix=git/libs/thread
#git://github.com/boostorg/chrono.git;destsuffix=git/libs/chrono
#git://github.com/boostorg/numeric.git;destsuffix=git/libs/numeric
#SRCREV = "${AUTOREV}"
