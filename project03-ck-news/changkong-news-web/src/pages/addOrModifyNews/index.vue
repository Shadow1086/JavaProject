<template>
    <div class="page">
        <div class="container">
            <div class="header">
                <input type="text" v-model="query.keyWords">
                <button>搜索</button>
                <button>写文章</button>
                <button @click="goback()">返回首页</button>
            </div>

            <div class="headline-list">
                <div class="headline" v-for="item in list" :key="item.hid">
                    <span>{{ item.title }}</span>
                    <span>{{ item.type }}</span>
                    <span>{{ item.publisher }}</span>
                    <span>{{ item.pastHours }}小时前更新</span>
                    <span>{{ item.pageViews }}</span>
                    <button @click="showDetail(item.hid)">查看详情</button>
                    <button @click="deleteHeadline(item.hid)">删除该文章</button>
                </div>
            </div>


        </div>
    </div>
</template>

<script setup lang="ts">
import {computed, ref, watch} from "vue";
import instance from "../../axios";
import {useRoute} from "vue-router";
import router from "../../routers/router";
import {hasToken} from "../../utils/token-auth";


interface news {
    hid: number;
    title: string;
    type: number;
    pageViews: number;
    pastHours: number;
    publisher: number;
}

interface pageInfo<T> {
    pageNum: number;
    pageSize: number;
    totalSize: number;
    totalPage: number;
    pageData: T[];
}

interface QueryParams {
    keyWords: string;
    type: number;
    pageNum: number;
    pageSize: number;
}

const route = useRoute();

// 定义新闻列表存放的对象
const list = ref<news[]>([]);
// 定义一页中的页码等数据
const pageInfo = ref<pageInfo<news> | null>();

const pageNumbers = computed(() => {
    if (!pageInfo.value || pageInfo.value.totalPage <= 0) {
        return []
    }
    return Array.from({length: pageInfo.value.totalPage}, (_, index) => index + 1);
})

const query = computed(() => ({
    keyWords: String(route.query.keyWords ?? ""),
    type: Number(route.query.type ?? 0),
    pageNum: Number(route.query.pageNum ?? 1),
    pageSize: Number(route.query.pageSize ?? 5)
}));

watch(
        () => route.query,
        () => {
            loadPage()
        }, {
            immediate: true
        }
)

async function loadPage() {
    if (hasToken()) {
        try {
            let {data} = await instance.post("/portal/findPageSelf", query.value);
            list.value = data.data.pageData ?? [];
            pageInfo.value = data.data
        } catch (e) {
            alert("查询列表错误：" + e);
        }
    } else {
        await router.push("login")
    }


}

// 更新查询的参数
function updateQuery(patch: Partial<QueryParams>) {
    router.push({
                path: "/addOrModifyNews",
                query: {
                    keyWords: String(patch.keyWords ?? query.value.keyWords),
                    type: Number(patch.type ?? query.value.type),
                    pageSize: Number(patch.pageSize ?? query.value.pageSize),
                    pageNum: Number(patch.pageNum ?? query.value.pageNum)
                }
            }
    )
}

function showDetail(hid: number) {
    if (hasToken()) {
        router.push({
            path: "/detail",
            query: {
                hid: Number(hid)
            }
        })
    } else {
        router.push("login")
    }
}

async function deleteHeadline(hid: number) {
    if (hasToken()) {
        let {data} = await instance.post("/headline/deleteHeadline", {"hid": hid});
        console.log(data.data)
        await loadPage();
    } else {
        await router.push("login")
    }

}

function goback() {
    router.push("headlineNews")
}
</script>

<style scoped>

</style>
